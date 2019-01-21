package com.experian.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.experian.dto.LoginRequest;
import com.experian.dto.LoginResponse;
import com.experian.entity.Authentication;
import com.experian.repository.UserRepository;
import com.experian.service.LoginService;

@RestController
@RequestMapping("/api")
@CrossOrigin(allowedHeaders="*", allowCredentials="true",  origins = "*", maxAge = 3600)
public class LoginController {

	@Autowired
	private LoginService service;
	
	@Resource
    private UserRepository userRepository;
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public LoginResponse userLogin(@RequestBody LoginRequest request){
		LoginResponse response = new LoginResponse();
		boolean isAuth = service.isUserAuthorized(request);
		if(isAuth) {
			response.setResponse("Successfully logged in");
		} else {
			response.setResponse("Authentication failed");
		}
		return response;
	}
	
	
	@RequestMapping(value="/save", method=RequestMethod.GET)
	public void saveUser() {
		Authentication user = new Authentication();
		user.setUsername("user1");
		user.setPassword("password");
		userRepository.save(user);
		
		Authentication user1 = new Authentication();
		user1.setUsername("user2");
		user1.setPassword("password");
		userRepository.save(user1);
	}
}
