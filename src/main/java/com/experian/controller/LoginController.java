package com.experian.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.experian.dto.LoginRequest;
import com.experian.service.LoginService;

@RestController
@RequestMapping("/api")
public class LoginController {

	@Autowired
	private LoginService service;
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String userLogin(LoginRequest request){
		boolean isAuth = service.isUserAuthorized(request);
		if(isAuth) {
			return "User: Successfully logged in";
		} else {
			return "User: Invalid username or password";
		}
	}
}
