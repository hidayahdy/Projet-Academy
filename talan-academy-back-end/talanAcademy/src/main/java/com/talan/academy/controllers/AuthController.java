package com.talan.academy.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.talan.academy.dto.LoginDto;
import com.talan.academy.dto.RegisterDto;
import com.talan.academy.dto.UserDto;
import com.talan.academy.dto.UserUpdateDto;
import com.talan.academy.enums.ERole;
import com.talan.academy.services.UserService;

@RestController
@RequestMapping("/talan/auth")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class AuthController {

	private final UserService userService;

	@Autowired
	public AuthController(UserService userService) {
		this.userService = userService;

	}

	
	  @PostMapping("/login") public ResponseEntity<Object>
	  authentification(@RequestBody LoginDto loginDto) { return new
	  ResponseEntity<>(userService.authentificationUser(loginDto), HttpStatus.OK);
	  }
	 

	@PostMapping("/register")
	public ResponseEntity<Object> register(@RequestBody RegisterDto registerDto) {

		Object response = userService.registerUser(registerDto);
		if (response instanceof String)
			return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
		return new ResponseEntity<>(response, HttpStatus.CREATED);

	}

	@GetMapping("/verify/{code}")
	public String verifyUser(@PathVariable String code) {
		if (userService.verify(code))
			return "verify_success";

		return "verify_fail";

	}

	@PutMapping
	public ResponseEntity<Object> updateUser(@RequestParam(value = "id") Long id,
			@RequestParam(value = "picture", required = false) MultipartFile picture) throws IOException {
		return new ResponseEntity<>(userService.updatePicture(id, picture), HttpStatus.OK);

	}

	@GetMapping("/{email}")
	public ResponseEntity<Object> findByEmail(@PathVariable String email) {
		return new ResponseEntity<>(userService.findUserByEmail(email), HttpStatus.OK);
	}

	@PatchMapping(value = "/{id}")
	public ResponseEntity<Object> updateAnnoce(@PathVariable Long id, @RequestBody UserUpdateDto annonceDto) {

		return ResponseEntity.ok().body(userService.updateUser(annonceDto, id));
	}

	@GetMapping(value = "/find-by-id/{id}")
	public ResponseEntity<UserUpdateDto> afficherParId(@PathVariable long id) {
		return ResponseEntity.ok().body(userService.findUserById(id));
	}

	@GetMapping(value = "/{role1}/{role2}/{page}/{size}")
	public ResponseEntity<Page<UserDto>> getUsersWithPagination(@PathVariable ERole role1, @PathVariable ERole role2,
			@PathVariable int page, @PathVariable int size) {
		return ResponseEntity.ok().body(userService.findByRolesNameWithPagination(role1, role2, page, size));
	}

	@GetMapping(value = "/filtered&paginated/{keyWord}/{page}/{size}")
	public Page<UserDto> searchUsersWithKeyWordPagination(@PathVariable String keyWord, @PathVariable int page,
			@PathVariable int size) {
		Pageable pageable = PageRequest.of(page, size);

		return userService.searchUsersKeyWordPaginated(keyWord, pageable);
	}

	@GetMapping(value = "/usersHaveApps/{page}/{size}")
	public Page<UserDto> findUsersWithApps(@PathVariable int page, @PathVariable int size) {

		return userService.findUsersWithApps(page, size);
	}

	@GetMapping(value = "/usersWithoutApps/{page}/{size}")
	public Page<UserDto> findUsersWithoutApps(@PathVariable int page, @PathVariable int size) {

		return userService.findUsersWithoutApps(page, size);
	}

	@PostMapping(value = "/activation/{id}")
	public ResponseEntity<Object> changeAccountStatus(@PathVariable Long id) {

		return ResponseEntity.ok().body(userService.changeAccountStatus(id));
	}

	@GetMapping(value = "/activationEmail/{email}")
	public ResponseEntity<Object> sendActivationEmail(@PathVariable String email) {

		return new ResponseEntity<>(userService.sendActivationEmail(email), HttpStatus.OK);
	}
	
	@PatchMapping(value = "/newEmail/{id}")
	public ResponseEntity<Object> resetEmail(@RequestBody String email,@PathVariable Long id) {

		return new ResponseEntity<>(userService.resetEmail(email,id), HttpStatus.OK);
	}

}
