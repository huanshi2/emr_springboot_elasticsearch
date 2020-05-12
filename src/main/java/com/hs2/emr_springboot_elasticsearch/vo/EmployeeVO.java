package com.hs2.emr_springboot_elasticsearch.vo;

import lombok.Data;

@Data
public class EmployeeVO {

    private String id;

    private Long age;

    private String name;

    private String sex;

    private String birthday;

    private String message;

    private long ageMax;

}
