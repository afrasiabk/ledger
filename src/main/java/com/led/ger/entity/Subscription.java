package com.led.ger.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status;

    @Transient
    private Long plan_id;

    @Transient
    private Long client_id;

    @ManyToOne
    private Plan plan;

    @JsonIgnore
    @ManyToOne
    private Client client;

//    @CreationTimestamp
//    private Timestamp created;
//
//    @UpdateTimestamp
//    private Timestamp updated;
}
