package com.example.demo.admin.controller.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.demo.admin.service.AdminRewardService;
import com.example.demo.data.RewardType;
import com.example.demo.memdb.table.dao.GameDao;
import com.example.demo.redis.repository.dao.GameRewardRedisDao;

@Controller
@RequestMapping("/admin/rest/reserve/reward")
public class AdminReserveRewardRestController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AdminRewardService adminRewardService;

    @RequestMapping(value="/{type}/list", method= {RequestMethod.POST})
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody List<GameRewardRedisDao> reserve_list(HttpServletResponse response, 
    		@RequestParam(value="pageNum") int pageNum, @RequestParam(value="viewCnt") int viewCnt
    		,@RequestParam(value="orderKey", required=false) String orderKey, @RequestParam(value="orderBy", required=false) String orderBy
    		,@PathVariable("type") String type) {
    	List<GameRewardRedisDao> list = null;
    	
    	try {
			GameRewardRedisDao vo = new GameRewardRedisDao();
    		vo.setPageNum(pageNum);
    		vo.setViewCnt(viewCnt);
    		vo.setGameId("");
    		vo.setRewardType(RewardType.valueOf(type.toUpperCase()));
    		
			list = adminRewardService.findAll(vo, "".equals(orderKey!=null?orderKey:"")?"millisec":orderKey, "desc".equalsIgnoreCase(orderBy!=null?orderBy:"asc")?false:true, "");
    	}catch(Exception e) {
    		log.error(e.toString());
    	}

        response.setStatus( HttpServletResponse.SC_OK  );
        return list;
    } 

    @RequestMapping(value="/{type}/list/{game_id}", method= {RequestMethod.POST})
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody List<GameRewardRedisDao> reserve_list_gameid(HttpServletResponse response, 
    		@RequestParam(value="pageNum") int pageNum, @RequestParam(value="viewCnt") int viewCnt
    		,@RequestParam(value="orderKey", required=false) String orderKey, @RequestParam(value="orderBy", required=false) String orderBy
    		,@PathVariable("type") String type ,@PathVariable("game_id") String game_id) {
    	List<GameRewardRedisDao> list = null;
    	
    	try {
			GameRewardRedisDao vo = new GameRewardRedisDao();
    		vo.setPageNum(pageNum);
    		vo.setViewCnt(viewCnt);
    		vo.setGameId(game_id);
    		vo.setRewardType(RewardType.valueOf(type.toUpperCase()));
    		
			list = adminRewardService.findAll(vo, "".equals(orderKey!=null?orderKey:"")?"millisec":orderKey, "desc".equalsIgnoreCase(orderBy!=null?orderBy:"asc")?false:true, "");
    	}catch(Exception e) {
    		log.error(e.toString());
    	}

        response.setStatus( HttpServletResponse.SC_OK  );
        return list;
    }

    @RequestMapping(value="/settle", method= {RequestMethod.POST})
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody Map<String, Object> settle(HttpServletResponse response, 
    		@RequestBody GameDao vo) {
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	try {
    		adminRewardService.settle(vo.getGameId());
    		resultMap.put("result", "success");
    	}catch(Exception e) {
    		resultMap.put("result", "fail");
    		log.error(e.toString());
    	}

        response.setStatus( HttpServletResponse.SC_OK  );
        return resultMap;
    }
}
