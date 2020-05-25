package com.hs2.emr_springboot_elasticsearch.service.impl;

import com.hs2.emr_springboot_elasticsearch.dto.EmployeeDTO;
import com.hs2.emr_springboot_elasticsearch.service.ESService;
import com.hs2.emr_springboot_elasticsearch.vo.EmployeeVO;
import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.rest.RestStatus;
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
import java.sql.ClientInfoStatus;
import java.util.ArrayList;
import java.util.List;

@Service
public class ESServiceImpl implements ESService {

    private static final Logger logger = LoggerFactory.getLogger(ESServiceImpl.class);

    @Autowired
    @Resource
    private RestHighLevelClient client;

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
    public String DeleteIndex(@RequestBody EmployeeVO employeeVO) {
        String response = null;
        try {
            DeleteRequest request = new DeleteRequest(employeeVO.getIndex());

            DeleteResponse deleteResponse = client.delete(request,RequestOptions.DEFAULT);

            RestStatus acknowledged = deleteResponse.status();

            response = "索引: " + employeeVO.getIndex() + "删除成功，返回：" + acknowledged;

        } catch (IOException e) {
            logger.error(e.toString());
        }
        return response;
    }

    @Override
    public String AddData(EmployeeVO employeeVO) {
        String response = null;
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

            IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);

            RestStatus acknowledged = indexResponse.status();

            response = "索引: " + employeeVO.getIndex() + "/_doc/" + employeeVO.getId() + " 添加成功，返回：" + acknowledged;

        } catch (IOException e) {
            logger.error(e.toString());
        }
        return response;
    }

    @Override
    public String UpdateData(@RequestBody EmployeeVO employeeVO) {
        String response = null;
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder()
                    .startObject()
                    .field("id",employeeVO.getId())
                    .field("name",employeeVO.getName())
                    .field("age",employeeVO.getAge())
                    .field("sex",employeeVO.getSex())
                    .field("message",employeeVO.getMessage())
                    .endObject();

            UpdateRequest request = new UpdateRequest(employeeVO.getIndex(),"_doc",employeeVO.getId());
            request.doc(builder);

            UpdateResponse updateresponse = client.update(request, RequestOptions.DEFAULT);

            RestStatus acknowledged = updateresponse.status();

            response = "索引: " + employeeVO.getIndex() + "/_doc/" + employeeVO.getId() + " 修改成功，返回：" + acknowledged;

        } catch (IOException e) {
            logger.error(e.toString());
        }
        return response;
    }

    @Override
    public String DeleteData(@RequestBody EmployeeVO employeeVO) {
        String response = null;
        try {
            DeleteRequest request = new DeleteRequest(employeeVO.getIndex(),"_doc",employeeVO.getId());

            DeleteResponse deleteresponse = client.delete(request, RequestOptions.DEFAULT);

            RestStatus acknowledged = deleteresponse.status();

            response = "索引: " + employeeVO.getIndex() + "/_doc/" + employeeVO.getId() + " 删除成功，返回：" + acknowledged;

        } catch (IOException e) {
            logger.error(e.toString());
        }
        return response;
    }

    @Override
    public List<EmployeeDTO> queryAll(@RequestBody(required = false) EmployeeVO employeeVO) {

        List<EmployeeDTO> list = new ArrayList<>();
        // 创建并设置SearchRequest对象
        SearchRequest searchRequest = new SearchRequest();
        // 设置request要搜索的索引和类型
        searchRequest.indices(employeeVO.getIndex());
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.fetchSource(new String[]{"id","age","name","sex","message"},new String[]{});

        searchSourceBuilder.from(0);
        searchSourceBuilder.size(100);

        try {
            searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

            SearchHits hits = searchResponse.getHits();
            SearchHit[] hits1 = hits.getHits();

            for (SearchHit str : hits1) {
                EmployeeDTO employeeDTO = JSON.parseObject(str.getSourceAsString(), EmployeeDTO.class);
                list.add(employeeDTO);
            }

        } catch (IOException e) {
            logger.error(e.toString());
        }
        return list;
    }

    public List<EmployeeDTO> SearchQuery(EmployeeVO employeeVO){
        /*
          @description: term query查询 该查询为精确查询（不会对查询条件进行分词），在查询时会以查询条件整体去匹配词库中的词（分词后的单个词）
         * @Param: [employeeVO]
         * @Return: java.util.List<com.hs2.emr_springboot_elasticsearch.dto.EmployeeDTO>
         * @Author: huanshi2
         * @Date: 2020/5/20 11:53
         */
        List<EmployeeDTO> list = new ArrayList<>();
        // 基础设置
        SearchRequest searchRequest = new SearchRequest(employeeVO.getIndex());
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.termQuery("name", employeeVO.getName()));
        searchRequest.source(searchSourceBuilder);
        try {
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits hits = searchResponse.getHits();
            // 得到匹配度高的文档
            SearchHit[] searchHits = hits.getHits();
            for (SearchHit hit : searchHits) {
                System.out.println(hit.getSourceAsString());
                EmployeeDTO employeeDTO = JSON.parseObject(hit.getSourceAsString(), EmployeeDTO.class);
                list.add(employeeDTO);
            }
        }catch (IOException e){
            logger.error(e.toString());
        }

        return list;
    }

    @Override
    public List<EmployeeDTO> MatchSearch(@RequestBody EmployeeVO employeeVO) {
        List<EmployeeDTO> list = new ArrayList<>();
        try {
            // 创建并设置SearchRequest对象
            SearchRequest searchRequest = new SearchRequest();
            // 设置request要搜索的索引和类型
            searchRequest.indices(employeeVO.getIndex()).types(employeeVO.getType());
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

            // 写法一：会将"spring实战"分成两个词，只有要有一个匹配成功，则返回该文档(Operator.OR)
            //searchSourceBuilder.query(QueryBuilders.matchQuery("description", "spring实战").operator(Operator.OR));

            // 写法二:只要有两个词匹配成功，则返回文档（如果是3个词，则是0.7*3，向下取整得到2，匹配到两个词则返回文档）
            searchSourceBuilder.query(QueryBuilders.matchQuery("message", employeeVO.getMessage())
                    .minimumShouldMatch(employeeVO.getMatchpercent()));

            searchRequest.source(searchSourceBuilder);
            // 发起请求，获取结果
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);


            System.out.println("searchRequest = " + searchRequest);
            System.out.println("searchSourceBuilder = " + searchSourceBuilder);
            System.out.println("searchResponse = " + searchResponse);

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
    public List<EmployeeDTO> MutimatchSearch(@RequestBody EmployeeVO employeeVO) {
        List<EmployeeDTO> list = new ArrayList<>();
        try {
            // 创建并设置SearchRequest对象
            SearchRequest searchRequest = new SearchRequest();
            // 设置request要搜索的索引和类型
            searchRequest.indices(employeeVO.getIndex()).types(employeeVO.getType());
            // 创建并设置SearchSourceBuilder对象
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            //限定需要的字段和不需要的字段
            searchSourceBuilder.fetchSource(new String[]{"_id","age","name","sex","birthday","message"},new String[]{});
            //输出结果排序 升序/降序
            searchSourceBuilder.sort("age", SortOrder.ASC);

            System.out.println(employeeVO.getMatchtext());

            // matchQuery（以name、descrption两个域为搜索域，并且至少匹配到70%的词）
            // field:添加一个字段以特定的增强值进行多重匹配。
            MultiMatchQueryBuilder multiMatchQueryBuilder =
                    QueryBuilders.multiMatchQuery(employeeVO.getMatchtext(), "message","name")
                            .minimumShouldMatch("70%")
                            .field("name", 10);

            searchSourceBuilder.query(multiMatchQueryBuilder);

            // 给搜索请求对象设置搜索源
            searchRequest.source(searchSourceBuilder);

            // 发起请求，获取结果
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);


            System.out.println("searchRequest = " + searchRequest);
            System.out.println("searchSourceBuilder = " + searchSourceBuilder);
            System.out.println("searchResponse = " + searchResponse);

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

        SearchRequest searchRequest = new SearchRequest();
        // 设置request要搜索的索引和类型
        searchRequest.indices(employeeVO.getIndex()).types(employeeVO.getType());
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.fetchSource(new String[]{"_id","age","name","sex","message"},new String[]{});
        searchSourceBuilder.sort("age", SortOrder.DESC);

        System.out.println(employeeVO.getSmallernumber());

        RangeQueryBuilder rangeQueryBuilder = QueryBuilders
                .rangeQuery("age")
                .from(employeeVO.getSmallernumber()).to(employeeVO.getLagernumber());

        searchSourceBuilder.query(rangeQueryBuilder);

        try {
            searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

            System.out.println("searchRequest = " + searchRequest);
            System.out.println("searchSourceBuilder = " + searchSourceBuilder);
            System.out.println("searchResponse = " + searchResponse);

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



}
