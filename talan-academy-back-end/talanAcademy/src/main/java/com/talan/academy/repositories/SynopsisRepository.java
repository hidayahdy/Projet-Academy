package com.talan.academy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.talan.academy.entities.Synopsis;

@Repository
public interface SynopsisRepository extends JpaRepository<Synopsis, Long>{

	Synopsis findByLessonId(Long id);
}
