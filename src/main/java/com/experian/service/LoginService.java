/**
 * 
 */
package com.experian.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.experian.UserRepository;
import com.experian.dto.LoginRequest;
import com.experian.entity.User;

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
		saveUser();
		User user = userRepository.findByUsername(login.getUserName());
		if(user != null) {
			if(login.getPassword().equalsIgnoreCase(user.getPassword())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * This internal method will saver user everytime as it is h2 database.
	 */
	private void saveUser() {
		User user = new User();
		user.setUsername("test1");
		user.setPassword("password");
		userRepository.save(user);
		
		User user2 = new User();
		user2.setUsername("test2");
		user2.setPassword("password");
		userRepository.save(user2);

		
	}

}
