package com.refugees.portal.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.refugees.portal.db.model.RefugeeUser;
import com.refugees.portal.db.service.RefugeeService;

@RestController
@RequestMapping("/Meessanger")
public class MessageController {
	@Autowired
	private RefugeeService refugeeService;
	final static Logger logger = LoggerFactory.getLogger(MessageController.class);
	@GetMapping
	public ResponseEntity<String> verifyWebhook(@RequestParam(value="username",required=false) String userName,
			@RequestParam(value="message",required=false) String textMessage) {
		try {
			if (userName != null) {
				RefugeeUser user = refugeeService.findRefugeeByUserName(userName);
				if(user!=null)
				ChatbotCallBack.sendTextMessage(user.getFacebookUserId(), textMessage);
			}
		} catch (Exception e) {

		}
		return ResponseEntity.ok("Ok");
	}
}
