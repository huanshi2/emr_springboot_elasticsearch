package com.hs2.emr_springboot_elasticsearch;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.hs2.emr_springboot_elasticsearch.dao")
public class EmrSpringbootElasticsearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmrSpringbootElasticsearchApplication.class, args);
    }

}
