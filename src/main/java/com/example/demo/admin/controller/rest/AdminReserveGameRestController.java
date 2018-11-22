package com.example.demo.admin.controller.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

import com.example.demo.admin.service.AdminGameService;
import com.example.demo.memdb.table.dao.GameDao;

@Controller
@RequestMapping("/admin/rest/reserve/game")
public class AdminReserveGameRestController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AdminGameService adminGameService;

    @RequestMapping(value="/list", method= {RequestMethod.GET, RequestMethod.POST})
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody List<GameDao> list(HttpServletResponse response, 
    		@RequestParam(value="pageNum") int pageNum, @RequestParam(value="viewCnt") int viewCnt
    		,@RequestParam(value="orderKey", required=false) String orderKey, @RequestParam(value="orderBy", required=false) String orderBy) {
    	List<GameDao> list = null;
    	
    	try {
    		GameDao vo = new GameDao();
    		vo.setPageNum(pageNum);
    		vo.setViewCnt(viewCnt);
    		list = adminGameService.findAll(vo, "".equals(orderKey!=null?orderKey:"")?"gameName":orderKey, "desc".equalsIgnoreCase(orderBy!=null?orderBy:"asc")?false:true);
    	}catch(Exception e) {
    		log.error(e.toString());
    	}

        response.setStatus( HttpServletResponse.SC_OK  );
        return list;
    }

    @RequestMapping(value="/open/list", method= {RequestMethod.POST})
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody List<GameDao> open_list(HttpServletResponse response, 
    		@RequestParam(value="pageNum") int pageNum, @RequestParam(value="viewCnt") int viewCnt
    		,@RequestParam(value="orderKey", required=false) String orderKey, @RequestParam(value="orderBy", required=false) String orderBy) {
    	List<GameDao> list = null;
    	
    	try {
    		GameDao vo = new GameDao();
    		vo.setPageNum(pageNum);
    		vo.setViewCnt(viewCnt);
    		list = adminGameService.findAllOpendGame(vo, "".equals(orderKey!=null?orderKey:"")?"gameName":orderKey, "desc".equalsIgnoreCase(orderBy!=null?orderBy:"asc")?false:true);
    	}catch(Exception e) {
    		log.error(e.toString());
    	}

        response.setStatus( HttpServletResponse.SC_OK  );
        return list;
    }

    @RequestMapping(value="/close/list", method= {RequestMethod.POST})
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody List<GameDao> close_list(HttpServletResponse response, 
    		@RequestParam(value="pageNum") int pageNum, @RequestParam(value="viewCnt") int viewCnt
    		,@RequestParam(value="orderKey", required=false) String orderKey, @RequestParam(value="orderBy", required=false) String orderBy) {
    	List<GameDao> list = null;
    	
    	try {
    		GameDao vo = new GameDao();
    		vo.setPageNum(pageNum);
    		vo.setViewCnt(viewCnt);
    		list = adminGameService.findAllClosedGame(vo, "".equals(orderKey!=null?orderKey:"")?"gameName":orderKey, "desc".equalsIgnoreCase(orderBy!=null?orderBy:"asc")?false:true);
    	}catch(Exception e) {
    		log.error(e.toString());
    	}

        response.setStatus( HttpServletResponse.SC_OK  );
        return list;
    }

    @RequestMapping(value="/info/{game_id}", method= {RequestMethod.POST})
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody GameDao info(HttpServletResponse response, 
    		@PathVariable("game_id") String game_id) {
    	GameDao data = null;
    	
    	try {
    		data = adminGameService.find(game_id);
    	}catch(Exception e) {
    		log.error(e.toString());
    	}

        response.setStatus( HttpServletResponse.SC_OK  );
        return data;
    }

    @RequestMapping(value="/save", method= {RequestMethod.POST})
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody Map<String, Object> save(HttpServletResponse response, 
    		@RequestBody GameDao vo) {
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	try {
    		if(adminGameService.find(vo.getGameId())!=null) {
    			adminGameService.update(vo);
    		}else {
    			vo.setGameId(UUID.randomUUID()+"");
    			vo.initMap();
    			adminGameService.insert(vo);
    		}
    		resultMap.put("result", "success");
    	}catch(Exception e) {
    		resultMap.put("result", "fail");
    		log.error(e.toString());
    	}

        response.setStatus( HttpServletResponse.SC_OK  );
        return resultMap;
    }

    @RequestMapping(value="/delete", method= {RequestMethod.POST})
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody Map<String, Object> delete(HttpServletResponse response, 
    		@RequestBody GameDao vo) {
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	try {
    		adminGameService.delete(vo);
    		resultMap.put("result", "success");
    	}catch(Exception e) {
    		resultMap.put("result", "fail");
    		log.error(e.toString());
    	}

        response.setStatus( HttpServletResponse.SC_OK  );
        return resultMap;
    }
}
