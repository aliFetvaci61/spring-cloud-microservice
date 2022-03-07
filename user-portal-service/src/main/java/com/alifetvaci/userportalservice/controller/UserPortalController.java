package com.alifetvaci.userportalservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alifetvaci.userportalservice.jms.Sender;
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
	private Sender sender;
	
	@PostMapping("/register")
	public String registerUser(@RequestBody User user) {
		user.setStatus(UserStatus.WAITING_APPROVAL);
		userRepository.save(user);
		StringBuilder builder = new StringBuilder();
		builder.append(user.getId()).append(";").append(user.getEmail()).append(";").append(user.getUsername());
		
		sender.send(builder.toString());

		return "Register User";

	}
	
	@PostMapping("/login")
	public String loginUser(@RequestBody Login login) {
		User user = userRepository.findByEmail(login.getEmail()).orElse(null);
		if(user.getStatus().equals(UserStatus.APPROVED) && login.getPassword().equals(user.getPassword())) {
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
