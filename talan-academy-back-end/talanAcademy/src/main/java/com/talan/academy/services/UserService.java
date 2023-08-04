package com.talan.academy.services;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.talan.academy.dto.JwtReponse;
import com.talan.academy.dto.LoginDto;
import com.talan.academy.dto.RegisterDto;
import com.talan.academy.dto.UserDto;
import com.talan.academy.dto.UserPictureDto;
import com.talan.academy.dto.UserUpdateDto;
import com.talan.academy.entities.User;
import com.talan.academy.enums.ERole;

public interface UserService {

	Object registerUser(RegisterDto registerDto);

	boolean existEmail(String email);

	String checkUser(String email);

	JwtReponse authentificationUser(LoginDto connectionDto);

	void sendVerificationEmail(User user, String siteURL) throws UnsupportedEncodingException, MessagingException;

	boolean verify(String verificationCode);

	UserUpdateDto findUserById(long id);

	UserUpdateDto findUserByEmail(String email);

	UserUpdateDto updateInformation(String email, String linkedin, String phone, String address);

	UserUpdateDto updateUser(UserUpdateDto userDto, Long id);

	UserPictureDto updatePicture(Long id, MultipartFile userPicture) throws IOException;

	boolean verifExtention(String fileName);

	User updateUserRole(Long id);

	User addUserSession(Long id, Long idSession);

	Page<UserDto> findByRolesNameWithPagination(ERole role1, ERole role2, int page, int size);

	Page<UserDto> searchUsersKeyWordPaginated(String keyWord, Pageable pageable);

	Object changeAccountStatus(Long id);

	Page<UserDto> findUsersWithApps(int page, int size);

	Page<UserDto> findUsersWithoutApps(int page, int size);

	UserDto sendActivationEmail(String email);
	
	Object resetEmail(String email,Long id);

}
