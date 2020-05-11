package com.hs2.emr_springboot_elasticsearch.controller;

import com.hs2.emr_springboot_elasticsearch.entity.Customer;
import com.hs2.emr_springboot_elasticsearch.service.CustomerService;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/getcustomerlist")
    public Map<String, Object> getCustomerList(@RequestParam(value = "pageNum" ) Integer pageNum,
                                               @RequestParam(value = "pageSize") Integer pageSize) {
        Map<String, Object> modelMap = new HashMap<>();
        PageHelper.startPage(pageNum,pageSize);
        List<Customer> customerList = customerService.getCustomerList();
        //返回列表信息
        modelMap.put("success", true);
        modelMap.put("customerList", customerList);
        //返回分页信息
        PageInfo<Customer> pageInfo = new PageInfo<>(customerList);
        modelMap.put("pageInfo",pageInfo);
        return modelMap;
    }

    @PostMapping("/del")
    public Map<String, Object> del(@RequestParam int cid) {
        Map<String, Object> modelMap = new HashMap<>();
        int del = customerService.del(cid);
        if (del > 0) {
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "删除失败");
        }
        return modelMap;
    }

    @PostMapping("/add")
    public Map<String, Object> add(@RequestBody Customer customer) {
        Map<String, Object> modelMap = new HashMap<>();
        int add = customerService.add(customer);
        if (add > 0) {
            modelMap.put("success", true);

        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "添加失败");
        }
        return modelMap;
    }

    @GetMapping("getCus")
    public Map<String, Object> getCus(@RequestParam String name) {
        Map<String, Object> modelMap = new HashMap<>();
        if (!"".equals(name)) {

            List<Customer> customerList = customerService.getCustomer(name);
            modelMap.put("success", true);
            modelMap.put("customerList", customerList);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入查询信息");
        }
        return modelMap;
    }

    @GetMapping("/getbyid")
    public Map<String, Object> queryById(@RequestParam Integer c_id) {
        Map<String, Object> modelMap = new HashMap<>();
        if (c_id != null) {
            Customer customer = customerService.queryById(c_id);
            modelMap.put("success", true);
            modelMap.put("customer", customer);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "id不能为空");
        }
        return modelMap;
    }

    @PostMapping("/updatecustomer")
    public Map<String, Object> updateCustomer(@RequestBody Customer customer) {
        Map<String, Object> modelMap = new HashMap<>();
            int i = customerService.updCustomer(customer);
            if (i > 0) {
                modelMap.put("success", true);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "提交失败");
            }
        return modelMap;
    }
}
