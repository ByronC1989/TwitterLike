package org.ac.cst8277.cox.byron.twitterlike.repo;

import org.ac.cst8277.cox.byron.twitterlike.beans.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer> {

}
