package com.hs2.emr_springboot_elasticsearch.controller;

import com.project.terminal.entity.User;
import com.project.terminal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestParam String username, @RequestParam String password) {
        Map<String, Object> modelMap = new HashMap<>();
        User user = userService.login(username);
        if (user != null) {
            if (!user.getUsername().equals(username)) {
                modelMap.put("success", false);
                modelMap.put("errMsg", "用户名不存在请先注册");
            }
            if (user.getPassword().equals(password)) {
                modelMap.put("success", true);
                modelMap.put("user", user);

            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "密码或者账户不正确");
            }


        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "密码或者账户不正确");
        }
        return modelMap;
    }

    @PostMapping("/reg")
    public Map<String, Object> reg(@RequestParam String username, String password) {
        Map<String, Object> modelMap = new HashMap<>();
        if (!"".equals(username) && !"".equals(password)) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            int add = userService.add(user);
            if (add > 0) {
                modelMap.put("success", true);

            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "注册失败");
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "注册信息不能为空");
        }
        return modelMap;
    }
}
