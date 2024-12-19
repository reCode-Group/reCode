package com.dev.reCode.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "conversions")
@Getter
@Setter
public class Conversion {

    @Id
    @Column(columnDefinition = "TEXT", nullable = false)
    private String  id;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(name = "origin_code", nullable = false)
    private String originCode; // Исходный код

    @Column(name = "target_code", nullable = false)
    private String targetCode; // Целевой код

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}

