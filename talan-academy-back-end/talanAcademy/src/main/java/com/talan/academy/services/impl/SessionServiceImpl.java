package com.talan.academy.services.impl;


import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.talan.academy.dto.CursusDto;
import com.talan.academy.dto.SessionDto;
import com.talan.academy.entities.Cursus;
import com.talan.academy.entities.Session;
import com.talan.academy.enums.ESessionStatus;
import com.talan.academy.enums.EcursusType;
import com.talan.academy.helpers.ModelMapperConverter;
import com.talan.academy.repositories.CursusRepository;
import com.talan.academy.repositories.SessionRepository;
import com.talan.academy.services.SessionService;

@Service
public class SessionServiceImpl implements SessionService{
	
	private SessionRepository sessionRepository;
	private CursusRepository cursusRepository;
	
	public SessionServiceImpl(SessionRepository sessionRepository, CursusRepository cursusRepository) {
		this.sessionRepository= sessionRepository;
		this.cursusRepository= cursusRepository;
	}

	@Override
	public List<SessionDto> getSessionByCursusId(Long id) {
		return ModelMapperConverter.mapAll( sessionRepository.findSessionByCursusId(id), SessionDto.class);
	}

	@Override
	public SessionDto getSessionById(Long id) {
		return ModelMapperConverter.map(sessionRepository.findById(id), SessionDto.class);
	}

	@Override
	public List<SessionDto> findByStatus() {
		return ModelMapperConverter.mapAll(sessionRepository.findByStatusEqualsAndCursusTypeIsOrderByCursusName(ESessionStatus.PLANNED, EcursusType.PUBLIC), SessionDto.class);
	}

	@Override
	public Page<SessionDto> getAllSessionPagination(int page, int size) {
		PageRequest pr = PageRequest.of(page, size).withSort(org.springframework.data.domain.Sort.by("creationDate").descending());
		return sessionRepository.findAll(pr).map(entity -> ModelMapperConverter.map(entity, SessionDto.class));
	}

	@Override
	@Transactional
	public SessionDto addSession(SessionDto session, Long idCursus) {
		Optional<Cursus> cursusRes = cursusRepository.findById(idCursus);
		if(!cursusRes.isPresent()) return null;
		session.setCursus(ModelMapperConverter.map(cursusRes.get(), CursusDto.class));
		return ModelMapperConverter.map(sessionRepository.save(ModelMapperConverter.map(session, Session.class)), SessionDto.class) ;
	}

	@Override
	public void deleteSession(Long id) {
		sessionRepository.deleteById(id);
	}

	
}
