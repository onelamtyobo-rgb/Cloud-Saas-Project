package com.cloudobserve.acceptance;

import com.cloudobserve.backend.models.Assessment;
import com.cloudobserve.backend.models.Company;
import com.cloudobserve.backend.models.Infrastructure;
import com.cloudobserve.backend.services.AssessmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreateCompanyAssessmentTest {

    private AssessmentService assessmentService;

    @BeforeEach
    void setUp() {
        assessmentService = new AssessmentService();
    }

    @Test
    void shouldCreateCompanyAssessmentWithValidInformation() {

        // Given
        Company company = new Company(
                "Acme Corporation",
                "Technology",
                "Medium"
        );

        Infrastructure infrastructure = new Infrastructure(
                "On-Premises"
        );

        // When
        Assessment assessment =
                assessmentService.createAssessment(company, infrastructure);

        // Then
        assertNotNull(assessment);
        assertNotNull(assessment.getId());
        assertEquals(company, assessment.getCompany());
        assertEquals(infrastructure, assessment.getInfrastructure());
    }

    @Test
    void shouldRejectAssessmentWhenCompanyInformationIsMissing() { ... }

    @Test
    void shouldRejectAssessmentWhenInfrastructureInformationIsMissing() { ... }

    @Test
    void shouldCreateAssessmentWithUniqueIdentifier() { ... }