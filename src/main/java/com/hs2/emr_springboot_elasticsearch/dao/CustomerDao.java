package com.hs2.emr_springboot_elasticsearch.dao;

import com.hs2.emr_springboot_elasticsearch.entity.Customer;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerDao {
    //查询所有客户信息
    List<Customer> queryCustomerList();
    //删除客户信息
    int delCustomer(int cid);
    //添加客户信息
    int addCustomer(Customer customer);
    //根据name筛选客户信息
    List<Customer> queryCustomer(@Param("c_name") String c_name);
    //根据id查询客户
    Customer queryById(@Param("c_id") Integer c_id);
    //更新
    int updateCustomer(Customer customer);
}
