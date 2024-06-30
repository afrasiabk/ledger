package com.led.ger.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition="Decimal(10,5) default '0'")
    private BigDecimal balance;

    @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Account> children;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    @JsonIgnore
    private Account parent;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_head_id", referencedColumnName = "id")
    @JsonIgnore
    private Account accountHead;

    @JsonIgnore
    @ManyToOne
    private GeneralLedger generalLedger;

    @JsonIgnore
    @OneToMany(mappedBy = "id")
    private List<Transaction> transactions;

    @JsonIgnore
    @ManyToOne
    private User createdBy;
//    @CreationTimestamp
//    private Timestamp created;
//
//    @UpdateTimestamp
//    private Timestamp updated;

}
