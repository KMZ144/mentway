package com.global.mentorship.security.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.global.mentorship.payment.service.PaymentMethodService;
import com.global.mentorship.security.dto.AuthResposne;
import com.global.mentorship.security.dto.RegisteredMentee;
import com.global.mentorship.security.dto.RegisteredMentor;
import com.global.mentorship.security.dto.UserDetailsImpl;
import com.global.mentorship.user.entity.Mentee;
import com.global.mentorship.user.entity.Mentor;
import com.global.mentorship.user.entity.Roles;
import com.global.mentorship.user.service.CategoryService;
import com.global.mentorship.user.service.MenteeService;
import com.global.mentorship.user.service.MentorService;
import com.global.mentorship.user.service.RolesService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthService {

	private final AuthenticationManager authManager;

	private final MenteeService menteeService;

	private final UserDetailsServiceImpl userDetailsService;

	private final CategoryService categoryService;

	private final RolesService rolesService;

	private final MentorService mentorService;

	private final PasswordEncoder passwordEncoder;

	private final JWTUtil jwtUtil;
	
	@Value("${systemPath}")
	private String systemPath;

	public AuthResposne authenticate(String email, String password) {
		authManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

		final UserDetailsImpl userDetails = userDetailsService.loadUserByUsername(email);
		return getJWTToken(userDetails);
	}

	private AuthResposne getJWTToken(UserDetailsImpl userDetails) {
		final String jwt = jwtUtil.generateToken(userDetails);
		return new AuthResposne(jwt, userDetails.getName(), userDetails.getEmail(), userDetails.getImgUrl(),
				userDetails.getId(),
				userDetails.getAuthorities().stream().map(authz -> authz.getAuthority()).collect(Collectors.toList()),
				userDetails.isHasPayment());

	}

	public AuthResposne registerMentor(RegisteredMentor registeredMentor) throws IOException {
		MultipartFile multipartFile = registeredMentor.getImg();
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		long size = multipartFile.getSize();
		String filecode = saveFile(fileName, multipartFile);
		Mentor mentor = new Mentor();
		Roles role = rolesService.findByName("MENTOR");
		mentor.setName(registeredMentor.getName());
		mentor.setEmail(registeredMentor.getEmail());
		mentor.setCategory(categoryService.findById(registeredMentor.getCategoryId()));
		mentor.setCoverLetter(registeredMentor.getCoverLetter());
		mentor.setImgUrl(filecode);
		mentor.setPassword(passwordEncoder.encode(registeredMentor.getPassword()));
		mentor.setRoles(Set.of(role));
		mentor.setValid(false);
		Mentor savedMentor = mentorService.insert(mentor);
		final UserDetailsImpl userDetails = new UserDetailsImpl(savedMentor);
		return getJWTToken(userDetails);
	}

	public AuthResposne registerMentee(RegisteredMentee registeredMentee) throws IOException {
		MultipartFile multipartFile = registeredMentee.getImg();
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		long size = multipartFile.getSize();
		String filecode = saveFile(fileName, multipartFile);
		Mentee mentee = new Mentee();
		Roles role = rolesService.findByName("MENTEE");
		mentee.setName(registeredMentee.getName());
		mentee.setEmail(registeredMentee.getEmail());
		mentee.setImgUrl(filecode);
		mentee.setPassword(passwordEncoder.encode(registeredMentee.getPassword()));
		mentee.setRoles(Set.of(role));
		Mentee savedMentee = menteeService.insert(mentee);
		final UserDetailsImpl userDetails = new UserDetailsImpl(savedMentee);
		return getJWTToken(userDetails);
	}

	private String saveFile(String fileName, MultipartFile multipartFile) throws IOException {
		Path uploadPath = Paths.get(systemPath);

		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}
		UUID uuid = UUID.randomUUID();
		String uuidString = uuid.toString().replace("-", "");
		String fileCode = uuidString.substring(0, 8);
		Path filePath = uploadPath.resolve(fileCode + "-" + fileName);
		try (InputStream inputStream = multipartFile.getInputStream()) {
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException ioe) {
			throw new IOException("Could not save file: " + fileName, ioe);
		}
		return filePath.toString();

	}

}
