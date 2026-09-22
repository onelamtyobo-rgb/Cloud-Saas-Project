package com.cloudobserve.backend.controllers;

import com.cloudobserve.backend.models.Company;
import com.cloudobserve.backend.models.Infrastructure;
import com.cloudobserve.backend.repositories.CompanyRepository;
import com.cloudobserve.backend.repositories.InfrastructureRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/companies")
@Tag(name = "Companies", description = "Company management APIs")
public class CompanyController {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private InfrastructureRepository infrastructureRepository;

    @GetMapping
    @Operation(summary = "Get all companies")
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get company by ID")
    public ResponseEntity<Company> getCompany(@PathVariable Long id) {
        return companyRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new company")
    public Company createCompany(@Valid @RequestBody Company company) {
        return companyRepository.save(company);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update company")
    public ResponseEntity<Company> updateCompany(@PathVariable Long id, @Valid @RequestBody Company companyDetails) {
        return companyRepository.findById(id)
                .map(company -> {
                    company.setName(companyDetails.getName());
                    company.setIndustry(companyDetails.getIndustry());
                    company.setEmployeeCount(companyDetails.getEmployeeCount());
                    company.setCurrentInfrastructure(companyDetails.getCurrentInfrastructure());
                    return ResponseEntity.ok(companyRepository.save(company));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete company")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        return companyRepository.findById(id)
                .map(company -> {
                    companyRepository.delete(company);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/infrastructure")
    @Operation(summary = "Add infrastructure for company")
    public ResponseEntity<Infrastructure> addInfrastructure(@PathVariable Long id, @Valid @RequestBody Infrastructure infrastructure) {
        return companyRepository.findById(id)
                .map(company -> {
                    infrastructure.setCompany(company);
                    return ResponseEntity.ok(infrastructureRepository.save(infrastructure));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
