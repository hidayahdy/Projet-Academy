package com.talan.academy.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.talan.academy.entities.Resource;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {

	List<Resource> findByLessonId(Long id);
}
