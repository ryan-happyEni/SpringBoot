package com.example.demo.web.controller.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

import com.example.demo.memdb.table.dao.GameDao;
import com.example.demo.redis.repository.dao.GameInstallRedisDao;
import com.example.demo.redis.repository.dao.GameReserveRedisDao;
import com.example.demo.web.service.GameService;

@Controller
@RequestMapping("/rest/reserve")
public class ReserveRestController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GameService gameService;

    @RequestMapping(value="/game/{type}/list", method= {RequestMethod.POST})
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody List<GameDao> list(HttpServletResponse response, HttpSession session,
    		@RequestParam(value="pageNum") int pageNum, @RequestParam(value="viewCnt") int viewCnt
    		,@PathVariable("type") String type) {
    	List<GameDao> list = null;
    	
    	try {
    		GameDao vo = new GameDao();
    		vo.setPageNum(pageNum);
    		vo.setViewCnt(viewCnt);
			String userId = session.getAttribute("session_user_id")!=null?(String)session.getAttribute("session_user_id"):"";
    		list = (List<GameDao>)gameService.findAll(vo, userId, "closeTime", false, type.equalsIgnoreCase("open")?false:true);
    	}catch(Exception e) {
    		log.error(e.toString());
    	}

        response.setStatus( HttpServletResponse.SC_OK  );
        return list;
    }
    
    @RequestMapping(value="/game/check", method= {RequestMethod.POST})
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody Map<String, Object> check(HttpServletResponse response, HttpSession session,
    		@RequestBody GameReserveRedisDao vo) {
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	try {
			if(session.getAttribute("session_user_id")!=null) {
				resultMap.put("result", "empty");
				vo.setUserId((String)session.getAttribute("session_user_id"));
				GameReserveRedisDao dvo = gameService.findReserve(vo);
	    		if(dvo!=null) {
	    			resultMap.put("result", "not_empty");
	    		}
			}else {
    			resultMap.put("result", "login");
			}
    	}catch(Exception e) {
    		resultMap.put("result", "fail");
    		log.error(e.toString());
    	}

        response.setStatus( HttpServletResponse.SC_OK  );
        return resultMap;
    }
    
    @RequestMapping(value="/game/save", method= {RequestMethod.POST})
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody Map<String, Object> save(HttpServletResponse response, HttpSession session, 
    		@RequestBody GameReserveRedisDao vo) {
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	try {
			if(session.getAttribute("session_user_id")!=null) {
				vo.setUserId((String)session.getAttribute("session_user_id"));
				int result = gameService.reserve(vo);
				resultMap.put("result", result);
			}else {
    			resultMap.put("result", "login");
			}
    	}catch(Exception e) {
    		resultMap.put("result", "fail");
    		log.error(e.toString());
    	}

        response.setStatus( HttpServletResponse.SC_OK  );
        return resultMap;
    }
    
    @RequestMapping(value="/game/delete", method= {RequestMethod.POST})
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody Map<String, Object> delete(HttpServletResponse response, HttpSession session,
    		@RequestBody GameReserveRedisDao vo) {
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	try {
			if(session.getAttribute("session_user_id")!=null) {
				vo.setUserId((String)session.getAttribute("session_user_id"));
				int result = gameService.cancel(vo);
				resultMap.put("result", result);
			}else {
    			resultMap.put("result", "login");
			}
    	}catch(Exception e) {
    		resultMap.put("result", "fail");
    		log.error(e.toString());
    	}

        response.setStatus( HttpServletResponse.SC_OK  );
        return resultMap;
    }
    
    @RequestMapping(value="/game/install", method= {RequestMethod.POST})
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody Map<String, Object> install(HttpServletResponse response, HttpSession session,
    		@RequestBody GameInstallRedisDao vo) {
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	try {
			if(session.getAttribute("session_user_id")!=null) {
				vo.setUserId((String)session.getAttribute("session_user_id"));
				int result = gameService.install(vo);
				resultMap.put("result", result);
			}else {
    			resultMap.put("result", "login");
			}
    	}catch(Exception e) {
    		resultMap.put("result", "fail");
    		log.error(e.toString());
    	}

        response.setStatus( HttpServletResponse.SC_OK  );
        return resultMap;
    }
}
