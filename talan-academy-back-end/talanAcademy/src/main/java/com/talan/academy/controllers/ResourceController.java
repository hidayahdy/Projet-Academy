package com.talan.academy.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.talan.academy.services.ResourceService;
@RestController
@RequestMapping("/talan/resource")
@CrossOrigin(origins = "http://localhost:4200")
public class ResourceController {

		private final ResourceService resourceService ;

		public ResourceController(ResourceService resourceService) {
			this.resourceService = resourceService;
		}

		@GetMapping("/{id}")
		public ResponseEntity<Object> getResourceByLessonId(@PathVariable Long id) {
			return new ResponseEntity<>(resourceService.findResourceByLessonId(id), HttpStatus.OK);
		}
}
