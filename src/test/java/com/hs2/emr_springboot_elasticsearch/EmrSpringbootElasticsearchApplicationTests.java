package com.hs2.emr_springboot_elasticsearch;

import ch.qos.logback.classic.Logger;
import com.hs2.emr_springboot_elasticsearch.dto.EmployeeDTO;
import com.hs2.emr_springboot_elasticsearch.vo.EmployeeVO;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.RequestBody;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


class EmrSpringbootElasticsearchApplicationTests {

    @Before
    public void createEm(){
        EmployeeVO employeeV1 = new EmployeeVO();
        employeeV1.setIndex("school");
        employeeV1.setType("university");
    }

    @Test
    public  void  CreateIndex(@RequestBody EmployeeVO employeeV1) {
        List<EmployeeDTO> list = new ArrayList<>();

        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200, "http")));
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder();
            builder.startObject();
            {
                builder.startObject();
                {
                    builder.field("mapping");
                    {
                        builder.startObject();
                        {
                            builder.field(employeeV1.getType());
                            {
                                builder.startObject("properties");
                                {
                                    builder.field("id").startObject().field("index", "false").field("type", "integer");
                                    builder.field("name").startObject().field("index", "true").field("type", "text");
                                    builder.field("age").startObject().field("index", "false").field("type", "integer");
                                    builder.field("sex").startObject().field("index", "false").field("type", "text");
                                    builder.field("message").startObject().field("index", "true").field("type", "text").field("analyzer", "ik_max_word");
                                }
                                builder.endObject();
                            }
                            builder.endObject();
                        }
                        builder.endObject();
                    }
                    builder.endObject();
                }
                builder.endObject();
            }
            builder.endObject();
            System.out.println(employeeV1.getIndex());
            CreateIndexRequest request = new CreateIndexRequest(employeeV1.getIndex());
            request.settings(Settings.builder().put("number_of_shards", 1).put("number_of_replicas", 0));
            request.mapping(builder);

            CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);

            System.out.println(createIndexResponse);

            client.close();

        } catch (IOException e) {
            Logger logger = null;
            logger.error(e.toString());
        }
    }

}
