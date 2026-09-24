package com.cloudobserve.backend.models;

import jakarta.persistence.*;

@Entity
@Table(name = "infrastructure")
public class Infrastructure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    private String serverType;
    private Integer serverCount;
    private String operatingSystems;
    private String databaseSystems;
    private Double monthlyITCost;
    private Boolean hasBackupSystem;
    private Boolean hasDisasterRecovery;

    // --- GETTERS AND SETTERS ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Company getCompany() { return company; }
    public void setCompany(Company company) { this.company = company; }

    public String getServerType() { return serverType; }
    public void setServerType(String serverType) { this.serverType = serverType; }

    public Integer getServerCount() { return serverCount; }
    public void setServerCount(Integer serverCount) { this.serverCount = serverCount; }

    public String getOperatingSystems() { return operatingSystems; }
    public void setOperatingSystems(String operatingSystems) { this.operatingSystems = operatingSystems; }

    public String getDatabaseSystems() { return databaseSystems; }
    public void setDatabaseSystems(String databaseSystems) { this.databaseSystems = databaseSystems; }

    public Double getMonthlyITCost() { return monthlyITCost; }
    public void setMonthlyITCost(Double monthlyITCost) { this.monthlyITCost = monthlyITCost; }

    public Boolean getHasBackupSystem() { return hasBackupSystem; }
    public void setHasBackupSystem(Boolean hasBackupSystem) { this.hasBackupSystem = hasBackupSystem; }

    public Boolean getHasDisasterRecovery() { return hasDisasterRecovery; }
    public void setHasDisasterRecovery(Boolean hasDisasterRecovery) { this.hasDisasterRecovery = hasDisasterRecovery; }
}