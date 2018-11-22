package com.example.demo.web.data;


import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import com.example.demo.web.vo.UserVo;

public class MyAuthenticaion extends UsernamePasswordAuthenticationToken {
    private  static final long serialVersionUID = 1L;

    UserVo user;

    public MyAuthenticaion (String id, String password, List<GrantedAuthority> grantedAuthorityList, UserVo user) {
        super(id, password, grantedAuthorityList);
        this.user = user;
    } 
    
    public UserVo getUser() {
    	return user;
    }
    
    public void setUser(UserVo user) {
    	this.user = user;
    }
}
