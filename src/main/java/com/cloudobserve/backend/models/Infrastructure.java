package com.cloudobserve.backend.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "infrastructure")
public class Infrastructure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    private String serverType; // physical, virtual, hybrid

    private Integer serverCount;

    private String operatingSystems;

    private String databaseSystems;

    private Double monthlyITCost;

    private Boolean hasBackupSystem;

    private Boolean hasDisasterRecovery;
}