package com.talan.academy.service;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.talan.academy.dto.SessionDto;
import com.talan.academy.entities.Cursus;
import com.talan.academy.entities.Session;
import com.talan.academy.enums.ESessionStatus;
import com.talan.academy.enums.EcursusType;
import com.talan.academy.repositories.CursusRepository;
import com.talan.academy.repositories.SessionRepository;
import com.talan.academy.services.impl.SessionServiceImpl;

@ExtendWith(MockitoExtension.class)
class SessionServiceImplTest {

	@Mock
	SessionRepository sessionRepository;
	@Mock
	CursusRepository cursusRepository;

	@InjectMocks
	SessionServiceImpl sessionService;

	@Test
	void testGetSessionByCursusId_success() throws Exception {
		Cursus cursus = new Cursus(1L, "java", "java-cursus.png", "cursus java with talan academy", true,
				EcursusType.PUBLIC, null);
		Session session1 = new Session(1L, null, null, ESessionStatus.PLANNED, cursus);
		Session session2 = new Session(1L, null, null, ESessionStatus.PLANNED, cursus);
		List<Session> sessionListExpected = new ArrayList<Session>(Arrays.asList(session1, session2));

		Mockito.when(sessionRepository.findSessionByCursusId(1L)).thenReturn(sessionListExpected);
		List<SessionDto> sessionListDt = sessionService.getSessionByCursusId(1L);

		assertEquals(sessionListExpected.size(), sessionListDt.size());
	}

	@Test
	void testGetSessionById_success() throws Exception {
		Cursus cursus = new Cursus(1L, "java", "java-cursus.png", "cursus java with talan academy", true,
				EcursusType.PUBLIC, null);
		Session session = new Session(1L, null, null, ESessionStatus.PLANNED, cursus);
		Mockito.when(sessionRepository.findById(1L)).thenReturn(Optional.of(session));
		SessionDto sessionResult = sessionService.getSessionById(1L);
		assertEquals(session.getStatus(), session.getStatus());

	}

	@Test
	void testFindByStatus_success() throws Exception {
		Cursus cursus = new Cursus(1L, "java", "java-cursus.png", "cursus java with talan academy", true,
				EcursusType.PUBLIC, null);
		Session session1 = new Session(1L, null, null, ESessionStatus.PLANNED, cursus);
		Session session2 = new Session(2L, null, null, ESessionStatus.PLANNED, cursus);
		List<Session> sessionListExpected = new ArrayList<Session>(Arrays.asList(session1, session2));
		Mockito.when(sessionRepository.findByStatusEqualsAndCursusTypeIsOrderByCursusName(session1.getStatus(),
				cursus.getType())).thenReturn(sessionListExpected);
		List<SessionDto> sessionListResult = sessionService.findByStatus();

		assertEquals(sessionListExpected.size(), sessionListResult.size());

	}

	@Test
	 void testGetAllSessionPagination_success() throws Exception {
		Cursus cursus = new Cursus(1L, "java", "java-cursus.png", "cursus java with talan academy", true,
				EcursusType.PUBLIC, null);
		Session session1 = new Session(1L, null, null, ESessionStatus.PLANNED, cursus);
		Session session2 = new Session(2L, null, null, ESessionStatus.PLANNED, cursus);
		List<Session> sessionList = new ArrayList<Session>(Arrays.asList(session1, session2));

		Pageable pageable = PageRequest.of(0, 2).withSort(Sort.by("creationDate").descending());
		Page<Session> page = new PageImpl<>(sessionList, pageable, sessionList.size());

		when(sessionRepository.findAll(pageable)).thenReturn(page);
		Page<SessionDto> expectedList = sessionService.getAllSessionPagination(0, 2);

		assertEquals(page.getSize(), expectedList.getSize());
		verify(sessionRepository, times(1)).findAll(pageable);

	}
	
	@Test
	void testAddSession_success() throws Exception{
		Cursus cursus = new Cursus(1L, "java", "java-cursus.png", "cursus java with talan academy", true,
				EcursusType.PUBLIC, null);
		Session session = new Session(1L, null, null, ESessionStatus.PLANNED, cursus);
		SessionDto sessionToAdd = new SessionDto(1L, null, null, ESessionStatus.PLANNED, null);
		
		Mockito.when(cursusRepository.findById(1L)).thenReturn(Optional.of(cursus));
		session.setCursus(cursus);
		Mockito.when(sessionRepository.save(session)).thenReturn(session);
		SessionDto sessionResult = sessionService.addSession(sessionToAdd, 1L);
		assertEquals(session.getStatus(), sessionResult.getStatus());
	}
	
	@Test
	 void testAddSessionWhenIdCursusNotFound() throws Exception{
		Cursus cursus = new Cursus(1L, "java", "java-cursus.png", "cursus java with talan academy", true,
				EcursusType.PUBLIC, null);
		Session session = new Session(1L, null, null, ESessionStatus.PLANNED, cursus);
		SessionDto sessionToAdd = new SessionDto(1L, null, null, ESessionStatus.PLANNED, null);
		
		Mockito.when(cursusRepository.findById(1L)).thenReturn(Optional.empty());
		SessionDto sessionResult = sessionService.addSession(sessionToAdd, 1L);
		assertNull(sessionResult);
	}

	@Test
	void testDeleteSession_success() throws Exception {
		Cursus cursus = new Cursus(1L, "java", "java-cursus.png", "cursus java with talan academy", true,
				EcursusType.PUBLIC, null);
		Session session = new Session(1L, null, null, ESessionStatus.PLANNED, cursus);
		
		sessionService.deleteSession(session.getId());
		verify(sessionRepository).deleteById(session.getId());

	}
}
