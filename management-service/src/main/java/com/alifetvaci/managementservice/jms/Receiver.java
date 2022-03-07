package com.alifetvaci.managementservice.jms;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;

import com.alifetvaci.managementservice.model.User;
import com.alifetvaci.managementservice.repository.UserRepository;

public class Receiver {
	
	@Autowired
	private UserRepository userRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(Receiver.class);

	private CountDownLatch latch = new CountDownLatch(1);

	public CountDownLatch getLatch() {
		return latch;
	}

	@JmsListener(destination = "users.q")
	public void receive(String message) {
		logger.info("received message='{}'", message);
		String[] split = message.split(";");
		User user = new User();
		user.setId(split[0]);
		user.setEmail(split[1]);
		user.setUsername(split[2]);
		userRepository.save(user);
		latch.countDown();
	}
}
