package com.global.mentorship.notification.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import sendinblue.ApiClient;
import sendinblue.Configuration;
import sendinblue.auth.ApiKeyAuth;
import sibApi.TransactionalEmailsApi;
import sibModel.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

@Service
public class EmailService {


	@Value("${sendInBlue.secret}")
	private String apiKey;
	public void sendEmail(String to, String subject, String body) {

		ApiClient defaultClient = Configuration.getDefaultApiClient();
		ApiKeyAuth apiKey = (ApiKeyAuth) defaultClient.getAuthentication("api-key");
		apiKey.setApiKey("YOUR API KEY");

		try {
			TransactionalEmailsApi api = new TransactionalEmailsApi();
			SendSmtpEmailSender sender = new SendSmtpEmailSender();
			sender.setEmail("example@example.com");
			sender.setName("John Doe");
			List<SendSmtpEmailTo> toList = new ArrayList<SendSmtpEmailTo>();
			SendSmtpEmailTo to1 = new SendSmtpEmailTo();
			to1.setEmail("example@example.com");
			to1.setName("John Doe");
			toList.add(to1);
			Properties params = new Properties();
			params.setProperty("parameter", "My param value");
			params.setProperty("subject", "New Subject");
			SendSmtpEmail sendSmtpEmail = new SendSmtpEmail();
			sendSmtpEmail.setSender(sender);
			sendSmtpEmail.setTo(toList);
			sendSmtpEmail.setHtmlContent(
					"<html><body><h1>This is my first transactional email {{params.parameter}}</h1></body></html>");
			sendSmtpEmail.setSubject("My {{params.subject}}");
			sendSmtpEmail.setParams(params);

			List<SendSmtpEmailTo1> toList1 = new ArrayList<SendSmtpEmailTo1>();
			SendSmtpEmailTo1 to11 = new SendSmtpEmailTo1();
			to11.setEmail("example1@example.com");
			to11.setName("John Doe");
			toList1.add(to11);
			List<SendSmtpEmailMessageVersions> messageVersions = new ArrayList<>();
			SendSmtpEmailMessageVersions versions1 = new SendSmtpEmailMessageVersions();
			SendSmtpEmailMessageVersions versions2 = new SendSmtpEmailMessageVersions();
			versions1.to(toList1);
			versions2.to(toList1);
			messageVersions.add(versions1);
			messageVersions.add(versions2);
			sendSmtpEmail.setMessageVersions(messageVersions);
			sendSmtpEmail.setTemplateId(1L);
			CreateSmtpEmail response = api.sendTransacEmail(sendSmtpEmail);
			System.out.println(response.toString());
		} catch (Exception e) {
			System.out.println("Exception occurred:- " + e.getMessage());
		}

	}
}
