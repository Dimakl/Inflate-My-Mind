package com.inflatemymind.models;


import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "expressions")
public class Expression implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter @NonNull private Long id;

    @Getter @Setter @NonNull private String expression;
    @Getter @Setter @NonNull private Boolean answer;

    @Getter @Setter @NonNull private Long contributorId;

    public Expression() {

    }

    public Expression(@NonNull String expression, @NonNull Boolean answer, @NonNull Long contributorId) {
        this.expression = expression;
        this.answer = answer;
        this.contributorId = contributorId;
    }
}
