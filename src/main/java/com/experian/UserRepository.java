/**
 * 
 */
package com.experian;



import org.springframework.data.jpa.repository.JpaRepository;
import com.experian.entity.User;

/**
 * @author Manchanda's
 *
 */

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}