/**
 * 
 */
package com.experian.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.experian.dto.LoginRequest;
import com.experian.entity.Authentication;
import com.experian.repository.UserRepository;

/**
 * @author Manchanda's
 *
 */

@Service
public class LoginService {

	@Autowired
    private UserRepository userRepository;

	/**
	 * This method will check whether user is authorize or not.
	 * @param login
	 * @return
	 */
	public boolean isUserAuthorized(LoginRequest login) {
		List<Authentication> userList = userRepository.findAll();
		System.out.println("userList "+userList.size());
		if(!userList.isEmpty()) {
			for (Authentication user : userList) {
				System.out.println("user.getUsername "+user.getUsername());
				System.out.println("user.getPassword "+user.getPassword());
				System.out.println("login.getUsername "+login.getUserName());
				System.out.println("login.getPassword "+login.getPassword());
				if(login.getUserName().equalsIgnoreCase(user.getUsername()) && 
						login.getPassword().equalsIgnoreCase(user.getPassword())) {
					return true;
				}
			}
			
		}
		return false;
	}
}
