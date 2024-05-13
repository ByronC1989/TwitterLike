package org.ac.cst8277.cox.byron.twitterlike.beans;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int messageID;
    @Setter
    private int userID;
    @Setter
    private String content;
    @Setter
    @Column(name="Post_Date")
    private String postDate;

}
