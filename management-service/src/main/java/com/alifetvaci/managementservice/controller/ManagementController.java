package com.alifetvaci.managementservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/management")
public class ManagementController {
	
	@GetMapping("/users")
	public String listUsers() {
		return "Hello World Management Service";

	}

}
