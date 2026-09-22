package com.cloudobserve.backend.services;

import com.cloudobserve.backend.models.Assessment;
import com.cloudobserve.backend.models.Company;
import com.cloudobserve.backend.models.Infrastructure;
import com.cloudobserve.backend.repositories.AssessmentRepository;
import com.cloudobserve.backend.repositories.CompanyRepository;
import com.cloudobserve.backend.repositories.InfrastructureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class AssessmentService {

    @Autowired
    private AssessmentRepository assessmentRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private InfrastructureRepository infrastructureRepository;

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
        int maxScore = 0;

        for (Map.Entry<String, Integer> entry : answers.entrySet()) {
            totalScore += entry.getValue();
            maxScore += 5; // Assuming each question is scored 0-5
        }

        return (totalScore * 100) / maxScore;
    }

    private String generateRecommendations(Assessment.AssessmentType type, int score) {
        StringBuilder recommendations = new StringBuilder();

        if (type == Assessment.AssessmentType.READINESS) {
            if (score < 50) {
                recommendations.append("Low readiness. Recommend: 1) Conduct infrastructure audit, 2) Train IT staff on cloud fundamentals, 3) Start with non-critical workloads.");
            } else if (score < 80) {
                recommendations.append("Moderate readiness. Recommend: 1) Develop migration roadmap, 2) Establish cloud governance policies, 3) Pilot with select applications.");
            } else {
                recommendations.append("High readiness. Recommend: 1) Begin phased migration, 2) Implement cloud cost management, 3) Optimize for cloud-native architectures.");
            }
        } else if (type == Assessment.AssessmentType.SECURITY) {
            if (score < 50) {
                recommendations.append("Critical security gaps. Recommend: 1) Implement identity and access management, 2) Enable encryption at rest and in transit, 3) Deploy security monitoring tools.");
            } else if (score < 80) {
                recommendations.append("Good security posture. Recommend: 1) Regular security audits, 2) Implement zero-trust architecture, 3) Enhance incident response procedures.");
            } else {
                recommendations.append("Excellent security. Recommend: 1) Continuous compliance monitoring, 2) Advanced threat detection, 3) Security automation.");
            }
        } else if (type == Assessment.AssessmentType.COMPLIANCE) {
            if (score < 50) {
                recommendations.append("Major compliance issues. Recommend: 1) Identify applicable regulations (GDPR, HIPAA, SOC2), 2) Implement data governance framework, 3) Conduct compliance gap analysis.");
            } else if (score < 80) {
                recommendations.append("Moderate compliance. Recommend: 1) Document compliance procedures, 2) Regular audits and assessments, 3) Employee compliance training.");
            } else {
                recommendations.append("Strong compliance. Recommend: 1) Automated compliance monitoring, 2) Regular third-party audits, 3) Stay updated on regulatory changes.");
            }
        }

        return recommendations.toString();
    }

    public Assessment getAssessment(Long id) {
        return assessmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assessment not found"));
    }
}
