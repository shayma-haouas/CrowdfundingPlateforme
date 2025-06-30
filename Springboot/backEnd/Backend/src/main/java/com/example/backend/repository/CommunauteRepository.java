package com.example.backend.repository;

import com.example.backend.entities.Communaute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunauteRepository extends JpaRepository<Communaute, Long> {

    List<Communaute> findByCreateurId(Long createurId);

}