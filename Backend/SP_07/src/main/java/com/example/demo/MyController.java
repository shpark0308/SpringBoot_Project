package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyController {
	
	@RequestMapping("/")
	public String root() {
		return "index";
	}
	
	@RequestMapping("/page1")
	public String page1() {
		return "page1";
	}
	
	@RequestMapping("/page2")
	public String page2() {
		return "page2";
	}


}
