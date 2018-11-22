package com.example.demo.config;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.demo.web.data.MyAuthenticaion;
import com.example.demo.web.service.AuthorizationService;
import com.example.demo.web.vo.UserVo;

@Component
public class AuthProvider implements AuthenticationProvider {

    @Autowired
    AuthorizationService authorizationService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String id = authentication.getName();
        String password = authentication.getCredentials().toString();

        return authenticate(id, password);
    }
    
    public Authentication authenticate(String id, String password) throws AuthenticationException {
        UserVo user = authorizationService.login(id, password);
        if (user == null) return null;
        
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<GrantedAuthority>();
        String role = "";
        switch (user.getUser_type()) {
            case 0:
                role = "ROLE_NO";
                break;
            case 1:
                role = "ROLE_USER";
                break;
            case 999:
                role = "ROLE_ADMIN";
                break;
        }


    	RequestAttributes rattr = RequestContextHolder.getRequestAttributes();
    	HttpSession session = ((ServletRequestAttributes) rattr).getRequest().getSession();
    	session.setAttribute("session_user_info", user);
    	session.setAttribute("session_user_id", user.getUser_id());
    	session.setAttribute("session_user_name", user.getUser_name());
    	session.setAttribute("session_user_type", user.getUser_type());
    	session.setAttribute("session_user_role", role);
    	
        grantedAuthorityList.add(new SimpleGrantedAuthority(role));
        return new MyAuthenticaion(id, password, grantedAuthorityList, user);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
