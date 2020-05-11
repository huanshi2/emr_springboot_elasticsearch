package com.hs2.emr_springboot_elasticsearch.service.impl;

import com.hs2.emr_springboot_elasticsearch.dao.CustomerDao;
import com.hs2.emr_springboot_elasticsearch.entity.Customer;
import com.hs2.emr_springboot_elasticsearch.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerDao customerDao;


    @Override
    public List<Customer> getCustomerList() {
        return customerDao.queryCustomerList();
    }

    @Override
    public List<Customer> getCustomer(String name) {
        return customerDao.queryCustomer(name);
    }

    @Override
    public int del(int cid) {
        return customerDao.delCustomer(cid);
    }

    @Override
    public int add(Customer customer) {
        return customerDao.addCustomer(customer);
    }

    @Override
    public int updCustomer(Customer customer) {
        return customerDao.updateCustomer(customer);
    }

    @Override
    public Customer queryById(Integer c_id) {
        return customerDao.queryById(c_id);
    }
}
