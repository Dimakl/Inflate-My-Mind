package com.inflatemymind.models;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "submissions")
public class Submission implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter @NonNull private Long id;

    @CreationTimestamp
    @Getter @Setter private LocalDateTime submissionTime;

    @Getter @Setter @NonNull private Long expressionId;
    @Getter @Setter @NonNull private Long userId;

    @Getter @Setter @NonNull private Boolean isCorrect;

    public Submission() {

    }

    public Submission(@NonNull Long expressionId, @NonNull Long userId, @NonNull Boolean isCorrect) {
        this.expressionId = expressionId;
        this.userId = userId;
        this.isCorrect = isCorrect;
    }
}
