package com.hs2.emr_springboot_elasticsearch.service;

import com.hs2.emr_springboot_elasticsearch.entity.Customer;

import java.util.List;


public interface CustomerService {
    List<Customer> getCustomerList();
    List<Customer> getCustomer(String name);
    int del(int cid);
    int add(Customer customer);
    int updCustomer(Customer customer);
    Customer queryById(Integer c_id);

}
