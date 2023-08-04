package com.talan.academy.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.talan.academy.services.SynopsisService;

@RestController
@RequestMapping("/talan/synopsis")
@CrossOrigin(origins = "http://localhost:4200")
public class SynopsisController {

	private final SynopsisService synopsisService ;

	public SynopsisController(SynopsisService synopsisService) {
		this.synopsisService = synopsisService;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> getSynopsisByLessonId(@PathVariable Long id) {
		return new ResponseEntity<>(synopsisService.findSynopsisByLessonId(id), HttpStatus.OK);
	}
	
}
