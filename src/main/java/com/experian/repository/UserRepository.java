/**
 * 
 */
package com.experian.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import com.experian.entity.Authentication;

/**
 * @author Manchanda's
 *
 */
public interface UserRepository extends JpaRepository<Authentication, Integer> {
	
}