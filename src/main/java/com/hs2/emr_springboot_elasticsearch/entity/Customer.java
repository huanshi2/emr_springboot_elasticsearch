package com.hs2.emr_springboot_elasticsearch.entity;

public class Customer {

    private Integer c_id;
    private String c_name;
    private String c_telephone;
    private String c_address;
    private String c_remark;

    public Integer getC_id() {
        return c_id;
    }

    public void setC_id(Integer c_id) {
        this.c_id = c_id;
    }

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public String getC_telephone() {
        return c_telephone;
    }

    public void setC_telephone(String c_telephone) {
        this.c_telephone = c_telephone;
    }

    public String getC_address() {
        return c_address;
    }

    public void setC_address(String c_address) {
        this.c_address = c_address;
    }

    public String getC_remark() {
        return c_remark;
    }

    public void setC_remark(String c_remark) {
        this.c_remark = c_remark;
    }
}
