package com.hs2.emr_springboot_elasticsearch.service;

import com.hs2.emr_springboot_elasticsearch.entity.User;


public interface UserService {

    User login(String username);
    int add(User user);
}
