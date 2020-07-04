package com.hs2.emr_springboot_elasticsearch.vo;

import lombok.Data;

@Data
public class EmployeeVO {

    private String id;

    private long age;

    private String name;

    private String sex;

    private String message;

    private String index;

    private String type;

    private String smallernumber;

    private String lagernumber;

    private String matchtext;

    private String matchpercent;

    private String matchfield;

    private String matchfield1;

    private String fieldvalue;

    private String sortfield;

    private Integer startpage;

    private Integer endpage;

}
