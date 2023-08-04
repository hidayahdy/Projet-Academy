package com.talan.academy.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.talan.academy.entities.Session;
import com.talan.academy.enums.ESessionStatus;
import com.talan.academy.enums.EcursusType;

public interface SessionRepository  extends JpaRepository<Session, Long>{
 List<Session> findSessionByCursusId(Long id);
 List<Session> findByStatusEqualsAndCursusTypeIsOrderByCursusName(ESessionStatus status , EcursusType type);
}
