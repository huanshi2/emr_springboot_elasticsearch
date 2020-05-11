package com.hs2.emr_springboot_elasticsearch.entity;

import lombok.Data;

@Data
public class User {
    private Integer uid;
    private String username;
    private String password;
}