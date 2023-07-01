package com.global.mentorship.payment.controller;

import java.util.ArrayList;
import java.util.List;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.global.mentorship.payment.dto.PaymentMethodDto;
import com.global.mentorship.payment.dto.StripeTokenDto;
import com.global.mentorship.payment.entity.PaymentMethod;
import com.global.mentorship.payment.repo.PaymentMethodRepo;
import com.global.mentorship.payment.service.PaymentMethodService;
import com.global.mentorship.security.dto.UserDetailsImpl;
import com.global.mentorship.user.entity.User;
import com.global.mentorship.user.service.UserService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Card;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.Token;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/payments")
public class PaymentController {
	
	private final PaymentMethodService paymentMethodService;
	
	private final UserService userService;
	
	
	
	@GetMapping("/{id}")
	PaymentMethodDto getPaymentByUserId(@PathVariable long id ) {
		return paymentMethodService.findPaymentMethodsByUserId(id);
	}
	
	@PostMapping("/create")	
	String createToken() throws StripeException {
		 PaymentIntent pt =  paymentMethodService.createToken(null);
		 return pt.getClientSecret();
	}
	
}
