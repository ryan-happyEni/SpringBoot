package com.example.demo.sampledata;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.InheeApplication;
import com.example.demo.mysql.repository.User;
import com.example.demo.web.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = InheeApplication.class)
@ActiveProfiles("local")
public class UserTest {
    
    @Autowired
    private UserService userService;
    
    @Test
    public void test() {
    	try {
    		System.out.println("###########");
    		User vo = new User();
    		vo.setUserId("test1@test.com");
    		vo.setUserName("Test1");
    		vo.setUserPass("1234");
    		vo.setuType(1);
    		
    		userService.save(vo);

    		vo = new User();
    		vo.setUserId("admin@test.com");
    		vo.setUserName("Admin");
    		vo.setUserPass("1234");
    		vo.setuType(999);
    		
    		userService.save(vo);
    		System.out.println("###########");
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
}
