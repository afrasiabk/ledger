package com.led.ger.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition="Decimal(10,5)")
    private BigDecimal amount;

    private String type;

    @JsonIgnore
    @OneToMany(mappedBy = "id")
    private List<JournalEntry> journalEntry;

    @JsonIgnore
    @ManyToOne
    private GeneralLedger generalLedger;

    @JsonIgnore
    @ManyToOne
    private User createdBy;

//    @CreationTimestamp
//    private Timestamp created;
//
//    @UpdateTimestamp
//    private Timestamp updated;

}
