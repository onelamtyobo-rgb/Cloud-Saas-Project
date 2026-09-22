package com.cloudobserve.backend.repositories;

import com.cloudobserve.backend.models.Infrastructure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InfrastructureRepository extends JpaRepository<Infrastructure, Long> {
    Optional<Infrastructure> findByCompanyId(Long companyId);
}
