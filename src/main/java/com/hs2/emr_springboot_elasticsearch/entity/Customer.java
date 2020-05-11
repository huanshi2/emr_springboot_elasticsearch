package com.hs2.emr_springboot_elasticsearch.entity;

import lombok.Data;

@Data
public class Customer {

    private Integer c_id;
    private String c_name;
    private String c_telephone;
    private String c_address;
    private String c_remark;
}
