package com.global.mentorship.notification.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.global.mentorship.user.entity.User;

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

	public void sendEmail(User receiver, String subject, String body) {

		ApiClient defaultClient = Configuration.getDefaultApiClient();
		// Configure API key authorization: api-key
		ApiKeyAuth apiKey = (ApiKeyAuth) defaultClient.getAuthentication("api-key");
		apiKey.setApiKey(this.apiKey);

		try {
			TransactionalEmailsApi api = new TransactionalEmailsApi();
			SendSmtpEmailSender sender = new SendSmtpEmailSender();
			sender.setEmail("mentway@iti.com");
			sender.setName("MentWay");
			List<SendSmtpEmailTo> toList = new ArrayList<SendSmtpEmailTo>();
			SendSmtpEmailTo to = new SendSmtpEmailTo();
			to.setEmail(receiver.getEmail());
			to.setName(receiver.getName());
			toList.add(to);
			Properties params = new Properties();
			params.setProperty("parameter", body);
			params.setProperty("subject", subject);
			SendSmtpEmail sendSmtpEmail = new SendSmtpEmail();
			sendSmtpEmail.setSender(sender);
			sendSmtpEmail.setTo(toList);
			sendSmtpEmail.setHtmlContent(
					"<html><body><h1>{{params.parameter}}</h1></body></html>");
			sendSmtpEmail.setSubject("My {{params.subject}}");
			sendSmtpEmail.setParams(params);
			CreateSmtpEmail response = api.sendTransacEmail(sendSmtpEmail);
			System.out.println(response.toString());
		} catch (Exception e) {System.out.println("exception "+ e.getMessage());	;	}
	}
}
