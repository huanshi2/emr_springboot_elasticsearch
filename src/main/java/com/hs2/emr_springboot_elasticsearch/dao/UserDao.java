package com.hs2.emr_springboot_elasticsearch.dao;

import com.project.terminal.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {
    User login(String username);
    int add(User user);
}
