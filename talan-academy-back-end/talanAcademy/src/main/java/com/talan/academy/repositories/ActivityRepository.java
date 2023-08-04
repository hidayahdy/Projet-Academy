package com.talan.academy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.talan.academy.entities.Activity;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

}
