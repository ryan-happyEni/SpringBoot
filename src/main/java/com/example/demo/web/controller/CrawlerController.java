package com.example.demo.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/crawler")
public class CrawlerController {

	@RequestMapping(value = { "", "/"})
	public String main() {
		return "crawler";
	}
}
