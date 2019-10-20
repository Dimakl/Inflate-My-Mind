package com.inflatemymind.models;

import com.inflatemymind.utility.HashSalt;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter @NonNull private Long id;

    @Getter @Setter @NonNull private String login;
    @Getter @NonNull private String password;
    @Getter @Setter @NonNull private Boolean isTeacher;
    @Getter @Setter @NonNull private String firstName;
    @Getter @Setter @NonNull private String secondName;


    public User() {

    }

    public User(String login, String password, Boolean isTeacher, String firstName, String secondName) {
        this.login = login;
        this.password = HashSalt.hashPassword(password);
        this.isTeacher = isTeacher;
        this.firstName = firstName;
        this.secondName = secondName;
    }

    public void setPassword(String password) {
        this.password = HashSalt.hashPassword(password);
    }
}
