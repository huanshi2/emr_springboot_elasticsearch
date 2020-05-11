package com.hs2.emr_springboot_elasticsearch.service.impl;

import com.hs2.emr_springboot_elasticsearch.dto.EmployeeDTO;
import com.hs2.emr_springboot_elasticsearch.service.ESService;
import com.hs2.emr_springboot_elasticsearch.vo.EmployeeVO;
import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ESServiceImpl implements ESService {

    private static final Logger logger = LoggerFactory.getLogger(ESServiceImpl.class);

    @Autowired
    private RestHighLevelClient client;

    public void save(@RequestBody EmployeeVO employeeVO) {

    }

    @Override
    public List<EmployeeDTO> query(@RequestBody EmployeeVO employeeVO) {

        List<EmployeeDTO> list = new ArrayList<>();
        try {
            SearchRequest searchRequest = new SearchRequest("emr");
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

            QueryBuilder matchQueryBuilder = QueryBuilders.rangeQuery("age").gte(employeeVO.getAge());
            SearchSourceBuilder query = searchSourceBuilder.query(matchQueryBuilder);


            searchSourceBuilder.from(0);
            searchSourceBuilder.size(5);

            searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

            System.out.println("searchRequest = " + searchRequest);
            System.out.println("query = " + query);

            System.out.println("searchResponse = " + searchResponse);

            //查询响应中取出结果
            SearchHits hits = searchResponse.getHits();
            SearchHit[] searchHits = hits.getHits();

            for (SearchHit hit : searchHits) {
                System.out.println(hit.getSourceAsString());

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
        SearchRequest searchRequest = new SearchRequest("emr");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        QueryBuilder query = searchSourceBuilder.query();

        searchSourceBuilder.from(0);
        searchSourceBuilder.size(5);

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


        }


        return list;
    }
}
