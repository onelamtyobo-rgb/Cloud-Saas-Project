package com.cloudobserve.backend.controllers;

import com.cloudobserve.backend.models.Assessment;
import com.cloudobserve.backend.services.AssessmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/assessments")
@Tag(name = "Assessments", description = "Cloud assessment APIs")
public class AssessmentController {

    @Autowired
    private AssessmentService assessmentService;

    @PostMapping("/company/{companyId}")
    @Operation(summary = "Create new assessment for a company")
    public ResponseEntity<Assessment> createAssessment(
            @PathVariable Long companyId,
            @RequestParam Assessment.AssessmentType type) {
        try {
            Assessment assessment = assessmentService.createAssessment(companyId, type);
            return ResponseEntity.ok(assessment);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/complete")
    @Operation(summary = "Complete assessment with answers")
    public ResponseEntity<Assessment> completeAssessment(
            @PathVariable Long id,
            @RequestBody Map<String, Integer> answers) {
        try {
            Assessment assessment = assessmentService.completeAssessment(id, answers);
            return ResponseEntity.ok(assessment);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get assessment by ID")
    public ResponseEntity<Assessment> getAssessment(@PathVariable Long id) {
        try {
            Assessment assessment = assessmentService.getAssessment(id);
            return ResponseEntity.ok(assessment);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/company/{companyId}")
    @Operation(summary = "Get all assessments for a company")
    public List<Assessment> getCompanyAssessments(@PathVariable Long companyId) {
        return assessmentService.getAssessment(companyId) != null ?
                List.of(assessmentService.getAssessment(companyId)) : List.of();
    }
}
