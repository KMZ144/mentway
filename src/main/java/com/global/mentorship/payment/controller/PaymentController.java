package com.global.mentorship.payment.controller;


import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.global.mentorship.payment.service.PaymentMethodService;
import com.global.mentorship.security.dto.UserDetailsImpl;
import com.stripe.exception.StripeException;
import com.stripe.model.AccountLink;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
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
	
	@GetMapping("/mentor")
	public AccountLink paymentor(Authentication auth) throws StripeException {
		UserDetailsImpl user = (UserDetailsImpl) auth.getPrincipal();
		
		return paymentMethodService.payMentorForService(null, 0L);
		 
	}
	
	@GetMapping("/checkout")
	public void checkOut() throws StripeException {
		com.stripe.param.checkout.SessionCreateParams params =
				  com.stripe.param.checkout.SessionCreateParams.builder()
				    .setMode(com.stripe.param.checkout.SessionCreateParams.Mode.PAYMENT)
				    .addLineItem(
				      com.stripe.param.checkout.SessionCreateParams.LineItem.builder()
				        .setPrice("{{PRICE_ID}}")
				        .setQuantity(1L)
				        .build()
				    )
				    .setPaymentIntentData(
				      com.stripe.param.checkout.SessionCreateParams.PaymentIntentData.builder()
				        .setApplicationFeeAmount(123L)
				        .setTransferData(
				          com.stripe.param.checkout.SessionCreateParams.PaymentIntentData.TransferData.builder()
				            .setDestination("{{CONNECTED_ACCOUNT_ID}}")
				            .build()
				        )
				        .build()
				    )
				    .setSuccessUrl("https://example.com/success")
				    .setCancelUrl("https://example.com/cancel")
				    .build();

				Session session = Session.create(params);

	}
	
}
