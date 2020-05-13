package com.hs2.emr_springboot_elasticsearch.service.impl;

import com.hs2.emr_springboot_elasticsearch.dto.EmployeeDTO;
import com.hs2.emr_springboot_elasticsearch.service.ESService;
import com.hs2.emr_springboot_elasticsearch.vo.EmployeeVO;
import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ESServiceImpl implements ESService {

    private static final Logger logger = LoggerFactory.getLogger(ESServiceImpl.class);

    @Autowired
    @Resource
    private RestHighLevelClient client;

    @Override
    public List<EmployeeDTO> update(@RequestBody EmployeeVO employeeVO) {
        return null;
    }

    @Override
    public String CreateIndex(@RequestBody EmployeeVO employeeVO) {
        String response = null;
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder()
                    .startObject()
                    .field("properties")
                    .startObject()
                    .field("id").startObject().field("type", "text").endObject()
                    .field("name").startObject().field("type", "text").endObject()
                    .field("age").startObject().field("type", "integer").endObject()
                    .field("sex").startObject().field("type", "text").endObject()
                    .field("message").startObject().field("type", "text").field("analyzer", "ik_max_word").endObject()
                    .endObject()
                    .endObject();

            CreateIndexRequest request = new CreateIndexRequest(employeeVO.getIndex());
            request.settings(Settings.builder().put("number_of_shards", 1).put("number_of_replicas", 0));
            request.mapping(builder);
            CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);

            response = "索引: " + employeeVO.getIndex() + "创建成功，返回：" + createIndexResponse.isAcknowledged();

        } catch (IOException e) {
            logger.error(e.toString());
        }
        return response;
    }

    @Override
    public List<EmployeeDTO> addData(EmployeeVO employeeVO) {
        List<EmployeeDTO> list = new ArrayList<>();
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder()
                    .startObject()
                    .field("id",employeeVO.getId())
                    .field("name",employeeVO.getName())
                    .field("age",employeeVO.getAge())
                    .field("sex",employeeVO.getSex())
                    .field("message",employeeVO.getMessage())
                    .endObject();
            IndexRequest request = new IndexRequest(employeeVO.getIndex(),"_doc",employeeVO.getId());
            request.source(builder);

            IndexResponse response = client.index(request, RequestOptions.DEFAULT);

        } catch (IOException e) {
            logger.error(e.toString());
        }
        return list;
    }

    @Override
    public List<EmployeeDTO> ExistQuery(@RequestBody EmployeeVO employeeVO) {
        List<EmployeeDTO> list = new ArrayList<>();
        try {
            // 创建并设置SearchRequest对象
            SearchRequest searchRequest = new SearchRequest();
            // 设置request要搜索的索引和类型
            searchRequest.indices("student","teacher").types("classone","classtwo");
            // 创建并设置SearchSourceBuilder对象
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            //从第几页开始
            searchSourceBuilder.from(0);
            //返回多少条数据
            searchSourceBuilder.size(10);
            //限定需要的字段和不需要的字段
            searchSourceBuilder.fetchSource(new String[]{"_id","age","name","sex","birthday","message"},new String[]{});
            //输出结果排序 升序/降序
            searchSourceBuilder.sort("age", SortOrder.ASC);

            QueryBuilder matchQueryBuilder = QueryBuilders.existsQuery("name").queryName(employeeVO.getName());

            SearchSourceBuilder query = searchSourceBuilder.query(matchQueryBuilder);

            searchRequest.source(searchSourceBuilder);

            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

            System.out.println("searchRequest = " + searchRequest);
            System.out.println("query = " + query);

            //System.out.println("searchResponse = " + searchResponse);

            //查询响应中取出结果
            SearchHits hits = searchResponse.getHits();
            SearchHit[] searchHits = hits.getHits();

            for (SearchHit hit : searchHits) {
                //System.out.println(hit.getSourceAsString());
                EmployeeDTO employeeDTO = JSON.parseObject(hit.getSourceAsString(), EmployeeDTO.class);
                list.add(employeeDTO);
            }

        } catch (IOException e) {
            logger.error(e.toString());
        }
        return list;
    }

    @Override
    public List<EmployeeDTO> RangeQuery(@RequestBody EmployeeVO employeeVO) {

        List<EmployeeDTO> list = new ArrayList<>();
        try {
            // 创建并设置SearchRequest对象
            SearchRequest searchRequest = new SearchRequest();
            // 设置request要搜索的索引和类型
            searchRequest.indices("student","teacher").types("classone","classtwo");
            // 创建并设置SearchSourceBuilder对象
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            //从第几页开始
            searchSourceBuilder.from(0);
            //返回多少条数据
            searchSourceBuilder.size(10);
            //限定需要的字段和不需要的字段
            searchSourceBuilder.fetchSource(new String[]{"_id","age","name","sex","birthday","message"},new String[]{});
            //输出结果排序 升序/降序
            searchSourceBuilder.sort("age", SortOrder.ASC);

            /*
            * 范围查询(Range Query)
            * gte() :范围查询将匹配字段值大于或等于此参数值的文档。
            * gt() :范围查询将匹配字段值大于此参数值的文档。
            * lte() :范围查询将匹配字段值小于或等于此参数值的文档。
            * lt() :范围查询将匹配字段值小于此参数值的文档。
            * from() 开始值 to() 结束值 这两个函数与includeLower()和includeUpper()函数配套使用。
            * includeLower(true) 表示 from() 查询将匹配字段值大于或等于此参数值的文档；
            * includeLower(false) 表示 from() 查询将匹配字段值大于此参数值的文档；
            * includeUpper(true) 表示 to() 查询将匹配字段值小于或等于此参数值的文档；
            * includeUpper(false) 表示 to() 查询将匹配字段值小于此参数值的文档；
            */

            QueryBuilder matchQueryBuilder = QueryBuilders.rangeQuery("age").gte(employeeVO.getAge()).lt(employeeVO.getAgeMax());

            SearchSourceBuilder query = searchSourceBuilder.query(matchQueryBuilder);

            searchRequest.source(searchSourceBuilder);

            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

            System.out.println("searchRequest = " + searchRequest);
            System.out.println("query = " + query);

            //System.out.println("searchResponse = " + searchResponse);

            //查询响应中取出结果
            SearchHits hits = searchResponse.getHits();
            SearchHit[] searchHits = hits.getHits();

            for (SearchHit hit : searchHits) {
                //System.out.println(hit.getSourceAsString());
                EmployeeDTO employeeDTO = JSON.parseObject(hit.getSourceAsString(), EmployeeDTO.class);
                list.add(employeeDTO);
            }

        } catch (IOException e) {
            logger.error(e.toString());
        }
        return list;
    }

    @Override
    public List<EmployeeDTO> queryAll() {

        List<EmployeeDTO> list = new ArrayList<>();
        // 创建并设置SearchRequest对象
        SearchRequest searchRequest = new SearchRequest();
        // 设置request要搜索的索引和类型
        searchRequest.indices("student");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        searchSourceBuilder.from(0);
        searchSourceBuilder.size(100);
        //查询所有数据
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());

        try {
            searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

            SearchHits hits = searchResponse.getHits();
            SearchHit[] hits1 = hits.getHits();

            for (SearchHit str : hits1) {
                EmployeeDTO employeeDTO = JSON.parseObject(str.getSourceAsString(), EmployeeDTO.class);
                list.add(employeeDTO);
            }

            client.close();

        } catch (IOException e) {
        }
        return list;
    }
}
