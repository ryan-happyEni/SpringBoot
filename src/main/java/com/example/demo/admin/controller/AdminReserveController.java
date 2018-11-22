package com.example.demo.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/reserve")
public class AdminReserveController {

	@RequestMapping(value = { "", "/"})
	public String main() {
		return "admin/reserve";
	}

	@RequestMapping(value = { "/game"})
	public String game() {
		return "admin/game";
	}

	@RequestMapping(value = { "/open"})
	public String reserve() {
		return "admin/open";
	} 

	@RequestMapping(value = { "/install"})
	public String install() {
		return "admin/install";
	}

	@RequestMapping(value = { "/reward"})
	public String reward() {
		return "admin/reward";
	}
}
