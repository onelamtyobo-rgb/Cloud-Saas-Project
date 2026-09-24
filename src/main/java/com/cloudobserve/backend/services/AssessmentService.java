package com.cloudobserve.backend.services;

import com.cloudobserve.backend.models.Assessment;
import com.cloudobserve.backend.models.Company;
import com.cloudobserve.backend.repositories.AssessmentRepository;
import com.cloudobserve.backend.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class AssessmentService {

    @Autowired
    private AssessmentRepository assessmentRepository;

    @Autowired
    private CompanyRepository companyRepository;

    public Assessment createAssessment(Long companyId, Assessment.AssessmentType type) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found"));

        Assessment assessment = new Assessment();
        assessment.setCompany(company);
        assessment.setType(type);
        assessment.setStatus("PENDING");

        return assessmentRepository.save(assessment);
    }

    public Assessment completeAssessment(Long assessmentId, Map<String, Integer> answers) {
        Assessment assessment = assessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new RuntimeException("Assessment not found"));

        int score = calculateScore(assessment.getType(), answers);
        String recommendations = generateRecommendations(assessment.getType(), score);

        assessment.setScore(score);
        assessment.setStatus("COMPLETED");
        assessment.setRecommendations(recommendations);
        assessment.setCompletedAt(LocalDateTime.now());

        return assessmentRepository.save(assessment);
    }

    private int calculateScore(Assessment.AssessmentType type, Map<String, Integer> answers) {
        int totalScore = 0;
        int maxScore = answers.size() * 5;

        for (Integer value : answers.values()) {
            totalScore += value;
        }

        return maxScore > 0 ? (totalScore * 100) / maxScore : 0;
    }

    private String generateRecommendations(Assessment.AssessmentType type, int score) {
        if (type == Assessment.AssessmentType.READINESS) {
            if (score < 50) {
                return "Low readiness. Recommend infrastructure audit and staff training.";
            } else if (score < 80) {
                return "Moderate readiness. Develop migration roadmap and governance policies.";
            } else {
                return "High readiness. Begin phased migration.";
            }
        } else {
            // This now cleanly handles COMPLIANCE (and any other future non-readiness types)
            if (score < 50) {
                return "Major compliance issues. Identify regulations and implement governance.";
            } else if (score < 80) {
                return "Moderate compliance. Document procedures and conduct audits.";
            } else {
                return "Strong compliance. Automated monitoring recommended.";
            }
        }
    }

    public Assessment getAssessment(Long id) {
        return assessmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assessment not found"));
    }
}