package com.example.backend.repository;

import com.example.backend.entities.Contribution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContributionRepository extends JpaRepository<Contribution, Long> {
    List<Contribution> findByCampagneId(Long campagneId);
    List<Contribution> findByContributeurId(Long contributeurId);
}