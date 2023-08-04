package com.talan.academy.controllers;

import java.sql.Date;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.talan.academy.dto.SessionDto;
import com.talan.academy.enums.ESessionStatus;
import com.talan.academy.services.SessionService;

@RestController
@RequestMapping("/talan/session")
@CrossOrigin(origins = "http://localhost:4200")
public class SessionController {

	private final SessionService sessionService ;

	public SessionController(SessionService sessionService) {
		this.sessionService = sessionService;
	} 
	
	@GetMapping
	public ResponseEntity<Object> getAllSessionByStatut(){

		return new ResponseEntity<>(sessionService.findByStatus(), HttpStatus.OK);
	}
	
	
	@GetMapping(path="/pages")
	public ResponseEntity<Object> getAllSessionsWithPagination(@RequestParam() int page, @RequestParam() int size){
		Page<SessionDto> sessions = sessionService.getAllSessionPagination(page, size);
		if(sessions.isEmpty())
			return new ResponseEntity<>("", HttpStatus.OK);
		return new ResponseEntity<>(sessions, HttpStatus.OK);
	}
	
	@PostMapping(path="/add")
	public ResponseEntity<Object> addSession(@RequestParam()Date date,@RequestParam()Long id){
		SessionDto session = new SessionDto();
		session.setStartDate(date);
		session.setStatus(ESessionStatus.PLANNED);
		SessionDto sessionResult = sessionService.addSession(session, id);
		return new ResponseEntity<>(sessionResult, HttpStatus.OK);
	}
	
	@DeleteMapping(path="/delete/{idSession}")
	public ResponseEntity<Object> deleteSession(@PathVariable(name = "idSession") Long idSession){
		sessionService.deleteSession(idSession);
		return new ResponseEntity<>("Session deleted successfully", HttpStatus.OK);
	}
}
