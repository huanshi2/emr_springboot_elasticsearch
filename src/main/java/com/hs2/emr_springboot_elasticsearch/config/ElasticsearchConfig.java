package com.hs2.emr_springboot_elasticsearch.config;

import lombok.Data;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "spring.es")
public class ElasticsearchConfig {

    // 集群地址，多个用,隔开
    @Value("${spring.es.host}")
    private String host;
    // 使用的端口号
    @Value("${spring.es.port}")
    private Integer port;
    // 使用的协议
    @Value("${spring.es.scheme}")
    private String scheme;

    @Bean(destroyMethod = "close")
    public RestHighLevelClient client() {
        //设置es节点的配置信息
        //在Elasticsearch 7.0.0 版本字后改配置已经过时不建议使用，
        //在Elasticsearch 8.0.0 版本中删除该方法的客户端的连接，
        //在Elasticsearch 7.0.0 版本中是通过TCP协议进行连接的，默认端口是9300，
        //实例化es的客户端对象  transport client 将在es8.0之后被淘汰
        //TransportClient client = new PreBuiltTransportClient(settings)
        //.addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"), 9300))
        //创建客户端连接ES 创建Bean对象, 当有多个集群时 可以创建多个 HttpHost, 指定不同的集群地址，本文只是为了学习只做了单实例的安装
        return new RestHighLevelClient(RestClient.builder(new HttpHost(host, port, scheme)));
    }

}



