package com.talan.academy.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.talan.academy.entities.Module;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {

	List<Module> findByCursusId(Long id);
}
