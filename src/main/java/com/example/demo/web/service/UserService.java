package com.example.demo.web.service;

import com.example.demo.mysql.repository.User;

public interface UserService {
    void save(User user);

    User findByUserId(String user_id);
    User findByUserName(String user_name);
}
