package com.example.demo.web.controller.rest;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.demo.redis.repository.dao.GameReserveRedisDao;
import com.example.demo.web.service.GameService;

@Controller
@RequestMapping("/prereservation")
public class OpenApiRestController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GameService gameService;

    @RequestMapping(value="/enroll/{preserv_id}/{user_id}", method= {RequestMethod.GET, RequestMethod.POST})
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody Map<String, String> enroll(HttpServletResponse response, HttpSession session
    		,@PathVariable("preserv_id") String preserv_id, @PathVariable("user_id") String user_id) {
    	Map<String, String> result = new HashMap<String, String>();
    	
    	try {

    		GameReserveRedisDao vo = new GameReserveRedisDao();
    		vo.setGameId(preserv_id);
			vo.setUserId(user_id);
    		
    		result.put("result", ""+gameService.reserve(vo));
    	}catch(Exception e) {
    		log.error(e.toString());
    	}

        response.setStatus( HttpServletResponse.SC_OK  );
        return result;
    }

    @RequestMapping(value="/count/{preserv_id}", method= {RequestMethod.GET, RequestMethod.POST})
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody Map<String, String> count(HttpServletResponse response, HttpSession session
    		,@PathVariable("preserv_id") String preserv_id) {
    	Map<String, String> result = new HashMap<String, String>();
    	
    	try {
    		result.put("cnt", ""+gameService.reserveCnt(preserv_id));
    	}catch(Exception e) {
    		log.error(e.toString());
    	}

        response.setStatus( HttpServletResponse.SC_OK  );
        return result;
    }

    @RequestMapping(value="/check/{preserv_id}/{user_id}", method= {RequestMethod.GET, RequestMethod.POST})
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody Map<String, String> check(HttpServletResponse response, HttpSession session
    		,@PathVariable("preserv_id") String preserv_id, @PathVariable("user_id") String user_id) {
    	Map<String, String> result = new HashMap<String, String>();
    	
    	try {
			result.put("result", ""+gameService.reserveNo(preserv_id, user_id));
    	}catch(Exception e) {
    		log.error(e.toString());
    	}

        response.setStatus( HttpServletResponse.SC_OK  );
        return result;
    }
}
