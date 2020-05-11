package com.hs2.emr_springboot_elasticsearch.service.impl;

import com.hs2.emr_springboot_elasticsearch.dao.UserDao;
import com.hs2.emr_springboot_elasticsearch.entity.User;
import com.hs2.emr_springboot_elasticsearch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User login(String username) {

        return userDao.login(username);
    }

    @Override
    public int add(User user) {

        return userDao.add(user);
    }
}
