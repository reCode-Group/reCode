package com.dev.reCode.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "subscription_types")
@Getter
@Setter
public class SubscriptionType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(nullable = false)
    String name;

    @Column(nullable = true)
    String description;

    @Column(name = "max_tokens_per_convert", nullable = false)
    int maxTokens;

    @Column(nullable = false)
    float price;

    @Column(nullable = false)
    int duration;

}
