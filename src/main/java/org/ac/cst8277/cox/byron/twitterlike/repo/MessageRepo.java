package org.ac.cst8277.cox.byron.twitterlike.repo;

import org.ac.cst8277.cox.byron.twitterlike.beans.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface MessageRepo extends JpaRepository<Message, Integer> {

    // Get messages produced by a specific producer
    @Query("from Message where userID = ?1")
    List<Message> findByProducerId(Integer id);

    // Get messages from a specific subscriber
    @Query(value = "SELECT MSG.* FROM message MSG " +
            "JOIN (SELECT * FROM subscription WHERE SubscriberID = ?1) " +
            "SUB ON MSG.UserID = SUB.UserID" , nativeQuery = true)
    List<Message> findBySubscriberId(Integer id);

}
