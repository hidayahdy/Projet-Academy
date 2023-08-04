package com.talan.academy.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.talan.academy.services.FileService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/talan/files")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@Slf4j
public class FileController {

	private final FileService storageService;

	@Autowired
	public FileController(FileService storageService) {
		this.storageService = storageService;
	}

	@PostMapping("/upload")
	public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
		String message = "";
		try {
			storageService.save(file);
			message = "Uploaded the file successfully: " + file.getOriginalFilename();
			return ResponseEntity.status(HttpStatus.OK).body(message);
		} catch (Exception e) {
			message = "Could not upload the file: " + file.getOriginalFilename() + "!";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
		}
	}

	@GetMapping(value = "/{filename:.+}", produces = MediaType.ALL_VALUE)
	@ResponseBody
	public ResponseEntity<Object> getFile(@PathVariable String filename) {
		Resource file = null;
		try {
			file = storageService.load(filename);
			if (file == null)
				return new ResponseEntity<>("CV non trouvé", HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			log.info("hello");
		}

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
				.body(file);

	}

}
