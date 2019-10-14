package com.inflatemymind.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {
    @Id @GeneratedValue
    @Getter @Setter private Long id;

    @Getter @Setter private String login;
    @Getter @Setter private String password;
    @Getter @Setter private Boolean isTeacher;
    @Getter @Setter private String FirstName;
    @Getter @Setter private String SecondName;

    public User(String login, String password, Boolean isTeacher, String firstName, String secondName) {
        this.login = login;
        this.password = password;
        this.isTeacher = isTeacher;
        FirstName = firstName;
        SecondName = secondName;
    }
}
