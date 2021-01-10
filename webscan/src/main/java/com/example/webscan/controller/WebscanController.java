package com.example.webscan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.webscan.service.WebScanService;

@RestController
@RequestMapping("/")
public class WebscanController {
	@Autowired
	private WebScanService webScan;
	
	
	@GetMapping("getwords")
	public String getWords(@RequestParam String url) {
		
		
		
		return webScan.getWords(url);
	}
}
