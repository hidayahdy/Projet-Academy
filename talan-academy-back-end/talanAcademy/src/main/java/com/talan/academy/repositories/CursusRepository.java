package com.talan.academy.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.talan.academy.entities.Cursus;
import com.talan.academy.enums.EcursusType;

@Repository
public interface CursusRepository extends JpaRepository<Cursus, Long> {
 List<Cursus> findByTypeAndVisible(EcursusType type, Boolean visible);
 
}
