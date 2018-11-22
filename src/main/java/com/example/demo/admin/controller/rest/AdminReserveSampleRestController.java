package com.example.demo.admin.controller.rest;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.demo.sample.SampleMake;

@Controller
@RequestMapping("/admin/rest/reserve/init")
public class AdminReserveSampleRestController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SampleMake make;

    @RequestMapping(value="/sample", method= {RequestMethod.POST})
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody String init_sample(HttpServletResponse response) {
    	try {

    		make.make(3);
		    
    	}catch(Exception e) {
    		log.error(e.toString());
    	}

        response.setStatus( HttpServletResponse.SC_OK  );
        return "success";
    }
}
