package com.talan.academy.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.talan.academy.dto.CursusDto;
import com.talan.academy.dto.SessionDto;
import com.talan.academy.enums.EcursusType;
import com.talan.academy.services.CursusService;
import com.talan.academy.services.SessionService;
import com.talan.academy.services.impl.CursusServiceImpl;
import com.talan.academy.services.impl.SessionServiceImpl;

@RestController
@RequestMapping("/talan/cursus")
@CrossOrigin(origins = "http://localhost:4200")
public class CursusController {

	private final CursusService cursusService;
	private final SessionService sessionService;
	

	@Autowired
	public CursusController(CursusServiceImpl cursusService, SessionServiceImpl sessionService) {
		this.cursusService = cursusService;
		this.sessionService= sessionService;
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getCursusById(@PathVariable Long id) {
		CursusDto cursus = cursusService.findCursusById(id);
		if (cursus == null)
			return new ResponseEntity<>("Cursus Not found in DataBase", HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(cursus, HttpStatus.OK);
	}
	
	@GetMapping("/all")
	public ResponseEntity<Object> getAllCursus(){
		List<CursusDto> cursus=  cursusService.getAllCursus();

		return new ResponseEntity<>(cursus, HttpStatus.OK);
	}
	@GetMapping("/public")
	public ResponseEntity<Object> getAllPublicCursus(){
		List<CursusDto> cursus=  cursusService.getAllCursusByTypeAndVisible(EcursusType.PUBLIC, true);
		if(cursus.isEmpty())
			return new ResponseEntity<>("Cursus not found", HttpStatus.OK);

		return new ResponseEntity<>(cursus, HttpStatus.OK);
	}
	
	
	
	@PostMapping()
	public ResponseEntity<Object> addCursus(@RequestParam(value="name")String name,@RequestParam(value="description")String description,@RequestParam(value="type")EcursusType type,@RequestParam(value="keyWords")String keyWords, @RequestParam(value = "cursusPicture",required = true) MultipartFile cursusPicture) throws IOException{
		CursusDto cursus= new CursusDto();
		cursus.setName(name);
		cursus.setDescription(description);
		cursus.setType(type);
		cursus.setKeyWords(keyWords.split(","));
		CursusDto cursusCreated= cursusService.addCursus(cursus, cursusPicture);
		return new ResponseEntity<>(cursusCreated, HttpStatus.OK);
	}
	
	@GetMapping(path = "/image")
    public byte[] getProfileImage(@RequestParam(value="pictureName")String pictureName) throws IOException {
		String userDirectory = System.getProperty("user.home");
        return Files.readAllBytes(
                Paths.get(userDirectory + userDirectory.substring(2, 3) + "uploads/" + pictureName));
    }
	
	@PutMapping(path="/update/{idCursus}")
	public ResponseEntity<Object> updateCursusVisibile(@PathVariable(name = "idCursus") Long idCursus,@RequestParam(name = "visible") boolean visible){
		CursusDto cursus = cursusService.updateCursusVisible(idCursus,visible);
		return new ResponseEntity<>(cursus,HttpStatus.OK);
	}
	@DeleteMapping(path="/delete/{idCursus}")
	public ResponseEntity<Object> deleteCursus(@PathVariable(name = "idCursus") Long idCursus){
		List<SessionDto> sessions = sessionService.getSessionByCursusId(idCursus);
		if(sessions.isEmpty()) {
			cursusService.deleteCursus(idCursus);
			return new ResponseEntity<>("Cursus supprimer avec success",HttpStatus.OK);	
		}
		return new ResponseEntity<>("Vous ne pouvez pas supprimer ce cursus",HttpStatus.OK);
		
		
	}
	
	@PutMapping(path="/update")
	public ResponseEntity<Object> updateCursus(@RequestBody() CursusDto cursusDto){
		CursusDto cursus = cursusService.updateCursus(cursusDto);
		return new ResponseEntity<>(cursus, HttpStatus.OK);
	}
	
	@GetMapping(path="/pages")
	public ResponseEntity<Object> getAllCursusByPagination(@RequestParam() int page, @RequestParam() int size){
		Page<CursusDto> cursus = cursusService.getAllCursusByPagination(page, size);
		if(cursus.isEmpty())
			return new ResponseEntity<>("", HttpStatus.OK);
		return new ResponseEntity<>(cursus, HttpStatus.OK);
	}
}
