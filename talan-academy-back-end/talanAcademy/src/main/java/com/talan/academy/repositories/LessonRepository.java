package com.talan.academy.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.talan.academy.entities.Lesson;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {

	List<Lesson> findByModuleId(Long id);
}
