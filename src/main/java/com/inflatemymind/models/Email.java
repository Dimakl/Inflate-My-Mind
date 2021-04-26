package com.inflatemymind.models;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "emails")
public class Email implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter @NonNull private Long id;

    @Getter @Setter @NonNull private String email;


    @Getter @Setter @NonNull private Boolean isValidated;

    public Email() {
        // TODO: change
        isValidated = true;
    }

    public Email(@NonNull Boolean isValidated, @NonNull String email) {
        this.isValidated = isValidated;
        this.email = email;
    }
}
