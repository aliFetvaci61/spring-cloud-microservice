package com.alifetvaci.userportalservice.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;



public class Sender {
	
	private static final Logger logger = LoggerFactory.getLogger(Sender.class);

	@Autowired
	private JmsTemplate jmsTemplate;

	public void send(String message) {
		logger.info("sending message='{}'", message);
		jmsTemplate.convertAndSend("users.q", message);
	}
}