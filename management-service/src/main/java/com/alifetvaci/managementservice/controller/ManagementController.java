package com.alifetvaci.managementservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alifetvaci.managementservice.model.User;
import com.alifetvaci.managementservice.repository.UserRepository;



@RestController
@RequestMapping("/management")
public class ManagementController {
	
	@Autowired
	private UserRepository userRepository;
	
	
	@GetMapping("/users")
	public String listUsers() throws InterruptedException {
		List<User> findAll = userRepository.findAll();
		return findAll.toString();

	}

}
