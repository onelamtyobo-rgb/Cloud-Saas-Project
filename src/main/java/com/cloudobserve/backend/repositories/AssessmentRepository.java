package com.cloudobserve.backend.repositories;

import com.cloudobserve.backend.models.Assessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssessmentRepository extends JpaRepository<Assessment, Long> {
    List<Assessment> findByCompanyId(Long companyId);
    List<Assessment> findByType(Assessment.AssessmentType type);
}
