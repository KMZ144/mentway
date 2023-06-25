package com.global.mentorship.config;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.global.mentorship.user.entity.Category;
import com.global.mentorship.user.entity.Mentee;
import com.global.mentorship.user.entity.Mentor;
import com.global.mentorship.user.entity.Roles;
import com.global.mentorship.user.entity.User;
import com.global.mentorship.user.repo.CategoryRepo;
import com.global.mentorship.user.repo.MenteeRepo;
import com.global.mentorship.user.repo.MentorRepo;
import com.global.mentorship.user.repo.RolesRepo;
import com.global.mentorship.user.repo.UserRepo;
import com.global.mentorship.videocall.entity.MenteesServices;
import com.global.mentorship.videocall.entity.Services;
import com.global.mentorship.videocall.repo.MenteesServicesRepo;
import com.global.mentorship.videocall.repo.ServicesRepo;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StartupConfig implements CommandLineRunner{

	
	private final MentorRepo mentorRepo;
	
	private final MenteeRepo menteeRepo;
	
	private final CategoryRepo categoryRepo;
	
	private final ServicesRepo servicesRepo;
	
	private final MenteesServicesRepo menteesServicesRepo;
	
	private final RolesRepo rolesRepo;
	
	private final PasswordEncoder passwordEncoder;
	
	private final UserRepo userRepo;
	
	@Override
	public void run(String... args) throws Exception {
		insertCategories();
		insertRoles();
		insertAdmin();
		insertMentors();
		insertMentees();
		insertServices();
		insertMenteesServices();
		
		
	}
	
	
	private void insertCategories() {
		Category cat1 = new Category();
		cat1.setName("grahic design");
		
		Category cat2 = new Category();
		cat2.setName("back end");
		
		Category cat3 = new Category();
		cat3.setName("front end");
		
		Category cat4 = new Category();
		cat4.setName("DevOps");
		
		categoryRepo.saveAll(Arrays.asList(cat1,cat2,cat3,cat4));
	}
	
	
	private void insertRoles() {
		Roles role1 = new Roles();
		role1.setRole("MENTOR");
		rolesRepo.save(role1);
		
		Roles role2 = new Roles();
		role2.setRole("MENTEE");
		rolesRepo.save(role2);
		
		Roles role3 = new Roles();
		role1.setRole("ADMIN");
		rolesRepo.save(role3);
	}
	
	private void insertAdmin() {
		User admin = new User();
		admin.setEmail("admin@admin.com");
		admin.setImgUrl("admin img url");
		admin.setName("admin");
		admin.setPassword(passwordEncoder.encode("123"));
		admin.setRoles(Set.of(rolesRepo.findById(3L).get()));
		userRepo.save(admin);
	}
	private void insertMentors() {
		Mentor mentor1 = new Mentor() ;
		mentor1.setName("kareem");
		mentor1.setCoverLetter("cover letter 1");
		mentor1.setEmail("kareem@gmail.com");
		mentor1.setValid(true);
		mentor1.setCvUrl("cv url 1");
		mentor1.setImgUrl("img url 1");
		mentor1.setPassword(passwordEncoder.encode("123"));
		mentor1.setCategory(categoryRepo.findById(1L).get());
		mentor1.setRoles(Set.of(rolesRepo.findById(1L).get()));
		
		Mentor mentor2 = new Mentor();
		mentor2.setName("mohamed sarhan");
		mentor2.setCoverLetter("cover letter 2");
		mentor2.setEmail("sarhan@gmail.com");
		mentor2.setValid(true);
		mentor2.setCvUrl("cv url 2");
		mentor2.setImgUrl("img url 2");
		mentor2.setPassword(passwordEncoder.encode("123"));
		mentor2.setCategory(categoryRepo.findById(2L).get());
		mentor2.setRoles(Set.of(rolesRepo.findById(1L).get()));

		
		Mentor mentor3 = new Mentor();
		mentor3.setName("ola ayad");
		mentor3.setCoverLetter("cover letter 3");
		mentor3.setEmail("ola@gmail.com");
		mentor3.setValid(true);
		mentor3.setCvUrl("cv url 3");
		mentor3.setImgUrl("img url 3");
		mentor3.setPassword(passwordEncoder.encode("123"));
		mentor3.setCategory(categoryRepo.findById(3L).get());
		mentor3.setRoles(Set.of(rolesRepo.findById(1L).get()));

		
		Mentor mentor4 = new Mentor();
		mentor4.setName("mohamed Amr");
		mentor4.setCoverLetter("cover letter 4");
		mentor4.setEmail("amr@gmail.com");
		mentor4.setValid(false);
		mentor4.setCvUrl("cv url 4");
		mentor4.setImgUrl("img url 4");
		mentor4.setPassword(passwordEncoder.encode("123"));
		mentor4.setCategory(categoryRepo.findById(4L).get());
		mentor4.setRoles(Set.of(rolesRepo.findById(1L).get()));

		
		Mentor mentor5 = new Mentor();
		mentor5.setName("eman osama");
		mentor5.setCoverLetter("cover letter 5");
		mentor5.setEmail("eman@gmail.com");
		mentor5.setValid(true);
		mentor5.setCvUrl("cv url 5");
		mentor5.setImgUrl("img url 5");
		mentor5.setPassword(passwordEncoder.encode("123"));
		mentor5.setCategory(categoryRepo.findById(1L).get());
		mentor5.setRoles(Set.of(rolesRepo.findById(1L).get()));

		
		Mentor mentor6 = new Mentor();
		mentor6.setName("Hossam hassan");
		mentor6.setCoverLetter("cover letter 6");
		mentor6.setEmail("hossam@gmail.com");
		mentor6.setValid(true);
		mentor6.setCvUrl("cv url 6");
		mentor6.setImgUrl("img url 6");
		mentor6.setPassword(passwordEncoder.encode("123"));
		mentor6.setCategory(categoryRepo.findById(2L).get());
		mentor6.setRoles(Set.of( rolesRepo.findById(1L).get()));

		
		
		mentorRepo.saveAll(Arrays.asList(mentor1,mentor2,mentor3,mentor4,mentor5,mentor6));

	}
	
	private void insertMentees() {
		Mentee mentee1 = new Mentee() ;
		mentee1.setName("mohamed");
		mentee1.setEmail("mohamed@mentee.com");
		mentee1.setPassword(passwordEncoder.encode("123"));
		mentee1.setImgUrl("menteel img url 1");
		mentee1.setRoles(Set.of(rolesRepo.findById(2L).get()));
		
		Mentee mentee2 = new Mentee() ;
		mentee2.setName("mahmoud");
		mentee2.setEmail("mahmoud@mentee.com");
		mentee2.setPassword(passwordEncoder.encode("123"));
		mentee2.setImgUrl("mentee3 img url 1");
		mentee2.setRoles(Set.of (rolesRepo.findById(2L).get()));

		
		
		
		Mentee mentee3 = new Mentee() ;
		mentee3.setName("ahmed");
		mentee3.setEmail("ahmed@mentee.com");
		mentee3.setPassword(passwordEncoder.encode("123"));
		mentee3.setImgUrl("mentee3 img url 1");
		mentee3.setRoles(Set.of( rolesRepo.findById(2L).get()));

		
		Mentee mentee4 = new Mentee() ;
		mentee4.setName("aly");
		mentee4.setEmail("aly@mentee.com");
		mentee4.setPassword(passwordEncoder.encode("123"));
		mentee4.setImgUrl("mentee4 img url 1");
		mentee4.setRoles(Set.of( rolesRepo.findById(2L).get()));

		
		Mentee mentee5 = new Mentee() ;
		mentee5.setName("huda");
		mentee5.setEmail("huda@mentee.com");
		mentee5.setPassword(passwordEncoder.encode("123"));
		mentee5.setImgUrl("mentee5 img url 1");
		mentee5.setRoles(Set.of( rolesRepo.findById(2L).get()));

		menteeRepo.saveAll(Arrays.asList(mentee1,mentee2,mentee3,mentee4,mentee5));
		
		
		
	}
	
	private void insertServices() {
		Services service1 = new Services();
		service1.setDetails("service details");
		service1.setDuration(1);
//		service1.setEndDate(LocalDateTime.now().plusDays(3));
		service1.setMentor(mentorRepo.findById(2L).get());
		
		Services service5 = new Services();
		service5.setDetails("service details 5");
		service5.setDuration(1);
//		service5.setEndDate(LocalDateTime.now().plusDays(3));
		service5.setMentor(mentorRepo.findById(3L).get());
		
		Services service2 = new Services();
		service2.setDetails("service details 2");
		service2.setDuration(1);
//		service2.setEndDate(LocalDateTime.now().plusDays(3));
		service2.setMentor(mentorRepo.findById(4L).get());
		
		Services service3 = new Services();
		service3.setDetails("service details 3");
		service3.setDuration(3);
//		service3.setEndDate(LocalDateTime.now().plusDays(1));
		service3.setMentor(mentorRepo.findById(5L).get());
		
		
		Services service4 = new Services();
		service4.setDetails("service details 4");
		service4.setDuration(2);
//		service4.setEndDate(LocalDateTime.now().plusDays(2));
		service4.setMentor(mentorRepo.findById(6L).get());
		
		servicesRepo.saveAll(Arrays.asList(service1,service2,service3,service4,service5));
	}
	
	private void insertMenteesServices() {
		MenteesServices menteesServices = new MenteesServices();
		menteesServices.setMentee(menteeRepo.findById(8L).get());
		menteesServices.setServices(servicesRepo.findById(1L).get());
		menteesServices.setApplicationDetails("mentee application1");
		menteesServices.setStartDate(LocalDateTime.now().plusDays(2));
		menteesServices.setMeetingUrl("url");
		menteesServices.setRate(5);
		menteesServices.setReport(null);
		menteesServices.setUploadedUrl("upload url");
		menteesServices.setAccepted(true);
		
		
		MenteesServices menteesServices2 = new MenteesServices();
		menteesServices2.setMentee(menteeRepo.findById(9L).get());
		menteesServices2.setServices(servicesRepo.findById(2L).get());
		menteesServices2.setApplicationDetails("mentee 2 application1");
		menteesServices2.setMeetingUrl("url");
		menteesServices2.setRate(3);
		menteesServices2.setStartDate(LocalDateTime.now().plusDays(1));
		menteesServices2.setReport(null);
		menteesServices2.setUploadedUrl("upload url");
		menteesServices2.setAccepted(true);
		
		MenteesServices menteesServices3 = new MenteesServices();
		menteesServices3.setMentee(menteeRepo.findById(10L).get());
		menteesServices3.setServices(servicesRepo.findById(2L).get());
		menteesServices3.setApplicationDetails("mentee 3 application1");
		menteesServices3.setMeetingUrl("url");
		menteesServices3.setRate(2);
		menteesServices3.setReport("report 1");
		menteesServices3.setUploadedUrl("upload url");
		menteesServices3.setAccepted(true);
		menteesServices3.setStartDate(LocalDateTime.now().plusDays(3));

		
		MenteesServices menteesServices4 = new MenteesServices();
		menteesServices3.setMentee(menteeRepo.findById(11L).get());
		menteesServices3.setServices(servicesRepo.findById(3L).get());
		menteesServices3.setApplicationDetails("mentee 3 application1");
		menteesServices3.setMeetingUrl("url");
		menteesServices3.setRate(4);
		menteesServices3.setReport(null);
		menteesServices3.setUploadedUrl("upload url");
		menteesServices3.setAccepted(true);
		menteesServices3.setStartDate(LocalDateTime.now().plusHours(5));
		
		menteesServicesRepo.saveAll(Arrays.asList(menteesServices,menteesServices2,menteesServices3,menteesServices4));
		
	}

}
