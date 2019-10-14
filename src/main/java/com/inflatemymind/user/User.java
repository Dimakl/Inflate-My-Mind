package com.inflatemymind.user;

import lombok.Getter;
import lombok.Setter;

public class User {
    @Getter @Setter private Long id;
    @Getter @Setter private String login;
    @Getter @Setter private String password;
    @Getter @Setter private Boolean isTeacher;
    @Getter @Setter private String FirstName;
    @Getter @Setter private String SecondName;

    public User(Long id, String login, String password, Boolean isTeacher, String firstName, String secondName) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.isTeacher = isTeacher;
        FirstName = firstName;
        SecondName = secondName;
    }
}
