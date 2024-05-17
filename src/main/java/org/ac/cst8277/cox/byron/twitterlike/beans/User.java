package org.ac.cst8277.cox.byron.twitterlike.beans;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private int userID;
    @Getter
    @Setter
    private String name;
    @Setter
    private String email;
    private String password;
    @Getter
    @Setter
    private String authToken;

}
