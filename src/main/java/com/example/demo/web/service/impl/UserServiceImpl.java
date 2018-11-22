package com.example.demo.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.mysql.repository.User;
import com.example.demo.mysql.repository.UserRepository;
import com.example.demo.web.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(User user) {
        user.setUserPass(bCryptPasswordEncoder.encode(user.getUserPass()));
        userRepository.save(user);
    }

    @Override
    public User findByUserId(String user_id) {
        return userRepository.findByUserId(user_id);
    }

    @Override
    public User findByUserName(String user_name) {
        return userRepository.findByUserName(user_name);
    }
}
