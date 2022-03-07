package com.alifetvaci.userportalservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alifetvaci.userportalservice.model.Login;
import com.alifetvaci.userportalservice.model.User;
import com.alifetvaci.userportalservice.model.UserStatus;
import com.alifetvaci.userportalservice.repository.UserRepository;


@RestController
@RequestMapping("/user-portal")
public class UserPortalController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@PostMapping("/register")
	public String registerUser(@RequestBody User user) {
		user.setPassword(encoder.encode(user.getPassword()));
		user.setStatus(UserStatus.WAITING_APPROVAL);
		userRepository.save(user);
		
		return "Register User";

	}
	
	@PostMapping("/login")
	public String loginUser(@RequestBody Login login) {
		User user = userRepository.findByEmail(login.getEmail()).orElse(null);
		if(user.getStatus().equals(UserStatus.APPROVED) && !encoder.matches(login.getPassword(), user.getPassword())) {
			return "Log out";
		}
		return "Logged User";

	}
	
	@PutMapping("/update")
	public String updateUser(@RequestBody User user) {
		User user1 = userRepository.findByEmail(user.getEmail()).orElse(null);
		user1.setUsername(user.getUsername());
		userRepository.save(user1);
		return "Update User";

	}

}
