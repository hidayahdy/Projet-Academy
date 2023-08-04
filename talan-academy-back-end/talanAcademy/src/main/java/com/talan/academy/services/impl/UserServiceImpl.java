package com.talan.academy.services.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.talan.academy.dto.JwtReponse;
import com.talan.academy.dto.LoginDto;
import com.talan.academy.dto.RegisterDto;
import com.talan.academy.dto.UserDto;
import com.talan.academy.dto.UserPictureDto;
import com.talan.academy.dto.UserUpdateDto;
import com.talan.academy.entities.Role;
import com.talan.academy.entities.Session;
import com.talan.academy.entities.User;
import com.talan.academy.enums.ERole;
import com.talan.academy.helpers.ModelMapperConverter;
import com.talan.academy.repositories.ApplicationRepository;
import com.talan.academy.repositories.RoleRepository;
import com.talan.academy.repositories.SessionRepository;
import com.talan.academy.repositories.UserRepository;
import com.talan.academy.services.FileService;
import com.talan.academy.services.UserService;
import com.talan.academy.util.JwtUtils;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	private final RoleRepository roleRepository;

	private final SessionRepository sessionRepository;

	private final ApplicationRepository appRepo;

	private final PasswordEncoder encoder;

	private final AuthenticationManager authenticationManager;

	private final JwtUtils jwtUtils;

	private List<String> extensions = Arrays.asList(".jpg", ".png", ".jpeg");

	private final FileService fileService;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder,
			AuthenticationManager authenticationManager, SessionRepository sessionRepository, FileService fileService,
			JwtUtils jwtUtils, ApplicationRepository appRepo) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.encoder = encoder;
		this.authenticationManager = authenticationManager;
		this.jwtUtils = jwtUtils;
		this.fileService = fileService;
		this.sessionRepository = sessionRepository;
		this.appRepo = appRepo;

	}

	@Autowired
	private JavaMailSender mailSender;

	public Object registerUser(RegisterDto registerDto) {
		if (existEmail(registerDto.getEmail()))
			return checkUser(registerDto.getEmail());
		User user = new User(registerDto.getFirstName(), registerDto.getLastName(), registerDto.getEmail(),
				encoder.encode(registerDto.getPassword()));

		Set<Role> roles = new HashSet<>();
		String error = "Error: Role is not found.";
		Role userRole = roleRepository.findByName(ERole.ROLE_REGISTRED).orElseThrow(() -> new RuntimeException(error));
		roles.add(userRole);

		user.setRoles(roles.iterator().next());
		String randomCode = RandomString.make(64);
		user.setVerificationCode(randomCode);
		user.setEnabled(false);
		user.setActivated(false);
		userRepository.save(user);
		sendVerificationEmail(user, randomCode);

		return ModelMapperConverter.map(userRepository.save(ModelMapperConverter.map(user, User.class)),
				RegisterDto.class);
	}

	@Override
	public boolean existEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	@Override
	public String checkUser(String email) {
		Optional<User> user = userRepository.findByEmail(email);
		if (!user.isPresent())
			return "erreur";

		User existUser = user.get();
		if (!existUser.isEnabled())
			return "Cette adresse e-mail est déjà utilisée,vérifier votre boite mail.";
		if (existUser.isEnabled() && !existUser.isActivated())
			return "Cette adresse e-mail est déjà utilisée , voulez vous activer votre compte ?";
		return "vous pouvez connecter";
	}

	@Override
	public JwtReponse authentificationUser(LoginDto loginDto) {
		 System.out.println(SecurityContextHolder.getContext().getAuthentication());
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
		 System.out.println(SecurityContextHolder.getContext().getAuthentication());
	
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());

		return new JwtReponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getPseudo(), roles);
	}

	@Override
	public void sendVerificationEmail(User user, String siteURL) {
		String toAddress = user.getEmail();
		String fromAddress = "hideyarbeai@gmail.com";
		String senderName = "Talan Academy";
		String subject = "Merci de vérifier votre inscription";
		String content = "Bonjour [[name]],<br>"
				+ "Votre inscription a bien été enregistrée. Veuillez confirmer votre adresse email afin d’activer votre compte.<br>"
				+ "<h3><a href=\"http://localhost:4200/verify/" + user.getVerificationCode()
				+ "\" target=\"_self\">Je confirme mon email</a></h3>" + "Equipe Talan Academy<br>";

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		try {
			helper.setFrom(fromAddress, senderName);
			helper.setTo(toAddress);
			helper.setSubject(subject);
			content = content.replace("[[name]]", user.getFirstName());
			String verifyURL = siteURL + "/verify/" + user.getVerificationCode();
			content = content.replace("[[URL]]", verifyURL);
			helper.setText(content, true);
		} catch (MessagingException | UnsupportedEncodingException e) {
			log.info("Could not send mail");
		}
		mailSender.send(message);

	}

	@Override
	public UserDto sendActivationEmail(String email) {
		Optional<User> user = userRepository.findByEmail(email);
		User foundUser = null;

		if (!user.isPresent())
			return null;

		foundUser = user.get();
		sendVerificationEmail(foundUser, foundUser.getVerificationCode());
		return ModelMapperConverter.map(foundUser, UserDto.class);

	}

	@Override
	public boolean verify(String verificationCode) {
		User user = userRepository.findByVerificationCode(verificationCode);

		if (user.isEnabled() && user.isActivated())
			return false;
		else {

			user.setEnabled(true);
			user.setActivated(true);
			userRepository.save(user);

			return true;
		}

	}

	@Override
	public UserPictureDto updatePicture(Long id, MultipartFile userPicture) throws IOException {

		Optional<User> user = userRepository.findById(id);
		if (!user.isPresent())
			return null;

		userPicture.getOriginalFilename();

		if (verifExtention(FilenameUtils.getExtension(userPicture.getOriginalFilename()))) {

			String pictureName = fileService.save(userPicture);

			user.get().setPicture(pictureName);
		}

		return ModelMapperConverter.map(userRepository.save(ModelMapperConverter.map(user, User.class)),
				UserPictureDto.class);

	}

	@Override

	public boolean verifExtention(String fileName) {
		for (String i : extensions) {
			if (i.contains(fileName)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public UserUpdateDto findUserById(long id) {

		Optional<User> user = userRepository.findById(id);
		if (!user.isPresent())
			return null;

		return ModelMapperConverter.map(user, UserUpdateDto.class);
	}

	@Override
	public UserUpdateDto findUserByEmail(String email) {
		return ModelMapperConverter.map(userRepository.findByEmail(email), UserUpdateDto.class);
	}

	@Override
	public UserUpdateDto updateInformation(String email, String linkedin, String phone, String address) {
		Optional<User> userOpt = userRepository.findByEmail(email);
		if (!userOpt.isPresent())
			return null;
		User user = userOpt.get();
		user.setLinkedin(linkedin);
		user.setPhone(phone);
		user.setAddress(address);
		return ModelMapperConverter.map(userRepository.save(user), UserUpdateDto.class);
	}

	@Override
	public User updateUserRole(Long id) {
		Optional<User> userOpt = userRepository.findById(id);
		if (!userOpt.isPresent())
			return null;
		User user = userOpt.get();
		Optional<Role> roleOpt = roleRepository.findByName(ERole.ROLE_STUDENT);
		if (!roleOpt.isPresent())
			return null;
		user.setRoles(roleOpt.get());

		return userRepository.save(user);
	}

	@Override
	public User addUserSession(Long id, Long idSession) {
		Optional<User> userOpt = userRepository.findById(id);
		if (!userOpt.isPresent())
			return null;
		User user = userOpt.get();
		Optional<Session> sessionOpt = sessionRepository.findById(idSession);
		if (!sessionOpt.isPresent())
			return null;
		user.setSession(sessionOpt.get());

		return userRepository.save(user);
	}

	@Override
	public UserUpdateDto updateUser(UserUpdateDto userDto, Long id) {
		Optional<User> userOpt = userRepository.findById(id);
		if (!userOpt.isPresent())
			return null;
		User user = userOpt.get();
		user.setAddress(userDto.getAddress());
		user.setEmail(user.getEmail());
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setLinkedin(userDto.getLinkedin());
		user.setPhone(userDto.getPhone());
		userRepository.save(user);
		return ModelMapperConverter.map(user, UserUpdateDto.class);
	}

	@Override
	public Page<UserDto> findByRolesNameWithPagination(ERole role1, ERole role2, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return userRepository.findByRolesNameOrRolesNameOrderByCreationDateDesc(role1, role2, pageable)
				.map(entity -> ModelMapperConverter.map(entity, UserDto.class));
	}

	@Override
	public Page<UserDto> searchUsersKeyWordPaginated(String keyWord, Pageable pageable) {

		return userRepository.findUsersByKeyWordPaginated(keyWord, pageable)
				.map(entity -> ModelMapperConverter.map(entity, UserDto.class));
	}

	@Override
	public Object changeAccountStatus(Long id) {

		Optional<User> userToSearch = userRepository.findById(id);

		if (!userToSearch.isPresent())
			return null;

		User user = userToSearch.get();
		user.setActivated(!user.isActivated());
		userRepository.save(user);

		return user.isActivated();

	}

	@Override
	public Page<UserDto> findUsersWithApps(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);

		return appRepo.findDistintUsers(pageable).map(entity -> ModelMapperConverter.map(entity, UserDto.class));
	}

	@Override
	public Page<UserDto> findUsersWithoutApps(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		List<Long> ids = appRepo.findDistintUsersId();

		return userRepository.findByIdNotInAndRolesNameNot(ids, ERole.valueOf("ROLE_ADMIN"), pageable)
				.map(entity -> ModelMapperConverter.map(entity, UserDto.class));
	}

	@Override
	public Object resetEmail(String email, Long id) {
		Optional<User> user = userRepository.findById(id);
		if (!user.isPresent())
			return null;
		User userPresent = user.get();
		if (userPresent.getEmail().equals(email))
			return ModelMapperConverter.map(userRepository.save(userPresent), UserDto.class);
		userPresent.setEmail(email);
		userPresent.setActivated(false);
		return ModelMapperConverter.map(userRepository.save(userPresent), UserDto.class);
	}

}