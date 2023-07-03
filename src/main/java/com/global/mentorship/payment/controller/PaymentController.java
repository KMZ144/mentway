package com.global.mentorship.payment.controller;


import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.global.mentorship.payment.service.PaymentMethodService;
import com.global.mentorship.security.dto.UserDetailsImpl;
import com.stripe.exception.StripeException;
import com.stripe.model.AccountLink;
import com.stripe.model.PaymentIntent;
import com.stripe.model.Topup;
import com.stripe.model.checkout.Session;
import com.stripe.param.TopupCreateParams;
import com.stripe.param.billingportal.SessionCreateParams;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/payments")
public class PaymentController {
	
	private final PaymentMethodService paymentMethodService;
	
	@PostMapping("/create")	
	ResponseEntity<Map<String,String>> createPayemntIntent(Authentication auth) throws StripeException {
		UserDetailsImpl user = (UserDetailsImpl) auth.getPrincipal();
		 PaymentIntent pt =  paymentMethodService.createPayemntMethod(user.getId());
		 Map<String, String> map = new HashMap<>();
		 map.put("clientSecret", pt.getClientSecret());
		 return ResponseEntity.ok(map);
	}
	
	@GetMapping("/valid")
	ResponseEntity<Map<String,Boolean>> setValidPaymentMethod(Authentication auth,@RequestParam String pamentIntentId) throws StripeException {
		UserDetailsImpl user = (UserDetailsImpl) auth.getPrincipal();
		 paymentMethodService.setValidPayemntMethod(user.getId(), pamentIntentId);
		 Map<String, Boolean> map = new HashMap<>();
		 map.put("valid", true);
		 return ResponseEntity.ok(map);
	}
	
	@GetMapping("validate/mentor")
	public ResponseEntity<Map<String,String>> validateMentorPayment(Authentication auth) throws StripeException {
		UserDetailsImpl user = (UserDetailsImpl) auth.getPrincipal();
		Map<String,String> map = new HashMap<>();
		map.put("validationUrl", paymentMethodService.validateMentorPayment(user.getId()));
		return  ResponseEntity.ok(map);
	}	
}
