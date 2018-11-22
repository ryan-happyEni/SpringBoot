package com.example.demo.web.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.mysql.repository.User;
import com.example.demo.web.data.MyAuthenticaion;
import com.example.demo.web.vo.UserVo;

@Service
public class AuthorizationService {

	
	@Autowired
	UserService userService;

    public UserVo login(String user_id, String password) {
    	UserVo userVo = new UserVo();
    	userVo.setUser_id(user_id);
    	

    	User user = userService.findByUserId(user_id);

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);
    	System.out.println("hashedPassword["+hashedPassword+"]");
    	if(user==null){
    		return null;
    	}else {
    		if(!passwordEncoder.matches(password, user.getUserPass())) { 
    			return null;
    		}

    		userVo.setUser_type(user.getuType());
    	}
		
        return userVo;
    }
    
    public boolean checkPassword(UserVo user, String password) {
    	boolean result = false;

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);
    	System.out.println("hashedPassword["+hashedPassword+"]");
    	if(user!=null && user.getPassword()!=null && password!=null){
    		if(passwordEncoder.matches(password, user.getPassword())) { 
    			result = true;
    		}
    	}
    	
    	return result;
    }

    public static UserVo getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof MyAuthenticaion)
            return ((MyAuthenticaion) authentication).getUser();
        return null;
    }

    public static void setCurrentUser(UserVo user) {
        ((MyAuthenticaion)
                SecurityContextHolder.getContext().getAuthentication()).setUser(user);
    }
} 
