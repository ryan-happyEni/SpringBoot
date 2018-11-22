package com.example.demo.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reserve")
public class ReserveController {

	@RequestMapping(value = { "", "/"})
	public String main() {
		return "reserve";
	}
	@RequestMapping(value = { "/install"})
	public String install() {
		return "install";
	}
	@RequestMapping(value = { "/api"})
	public String api() {
		return "api";
	}
}
