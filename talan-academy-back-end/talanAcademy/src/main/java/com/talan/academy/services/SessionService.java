package com.talan.academy.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.talan.academy.dto.SessionDto;



public interface SessionService {
	
  List<SessionDto> getSessionByCursusId(Long id);
  SessionDto getSessionById(Long id ) ; 
  List<SessionDto> findByStatus();
  Page<SessionDto> getAllSessionPagination (int page, int size);
  SessionDto addSession(SessionDto session, Long idCursus);
  void deleteSession(Long id);
  
}
