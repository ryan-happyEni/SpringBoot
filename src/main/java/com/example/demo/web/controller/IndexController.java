package com.example.demo.web.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {

	@RequestMapping(value = { "", "/"})
	public String index(HttpSession session) {
		return "index";
	}

    @RequestMapping(value="login", method=RequestMethod.GET)
	public String login() {
		return "login";
	}
}
