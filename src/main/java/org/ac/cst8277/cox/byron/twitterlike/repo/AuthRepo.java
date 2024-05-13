package org.ac.cst8277.cox.byron.twitterlike.repo;

import org.ac.cst8277.cox.byron.twitterlike.beans.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AuthRepo extends JpaRepository<User, Integer> {

    // Check if user exists in database
    @Query(value = "SELECT user.* FROM user where name = ?1 AND password = ?2" , nativeQuery = true)
    User authorizeUser(String name, String password);

    // update user to have a token
    @Query(value = "SELECT user.* FROM user where AuthToken = ?1" , nativeQuery = true)
    User checkToken(String authToken);
}
