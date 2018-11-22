package com.example.demo.sampledata;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.InheeApplication;
import com.example.demo.admin.service.AdminGameService;
import com.example.demo.memdb.table.GameTable;
import com.example.demo.memdb.table.dao.GameDao;
import com.example.demo.sample.SampleMake;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = InheeApplication.class)
public class SampleDateMakeTest {

    @Autowired
    private AdminGameService adminGameService;

    @Autowired
    SampleMake make;
    
    @Test
    public void test() {
    	try {

    		System.out.println("###########");
    		GameDao vo = new GameDao();
    		List<GameDao> list = adminGameService.findAll(vo, "gameName", true);
    		if(list!=null) {
    			for(GameDao dvo : list) {
    				System.out.println(dvo);
    			}
    		}
    		System.out.println("###########");
    		make.make(3);
	        
    		vo = new GameDao();
    		list = adminGameService.findAll(vo, "gameName", true);
    		if(list!=null) {
    			for(GameDao dvo : list) {
    				System.out.println(dvo);
    			}
    		}

    		Map<String, GameDao> map = GameTable.getInstance();
    		System.out.println("###########");
    		
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
}