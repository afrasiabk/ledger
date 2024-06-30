package com.led.ger.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer days;

    @JsonIgnore
    @OneToMany(mappedBy = "id")
    private List<Subscription> subscriptions;

//    @CreationTimestamp
//    private Timestamp created;
//
//    @UpdateTimestamp
//    private Timestamp updated;
}
