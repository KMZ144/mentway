package com.global.mentorship.payment.service;

import com.global.mentorship.base.service.BaseService;
import com.global.mentorship.error.NoPayemntMethodException;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.global.mentorship.payment.dto.PaymentMethodDto;
import com.global.mentorship.payment.dto.StripeTokenDto;
import com.global.mentorship.payment.entity.PaymentMethod;
import com.global.mentorship.payment.mapper.PaymentMapper;
import com.global.mentorship.payment.repo.PaymentMethodRepo;
import com.global.mentorship.security.dto.UserDetailsImpl;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.Token;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.PaymentMethodCreateParams;
import com.stripe.param.PaymentMethodCreateParams.CardDetails;
import com.stripe.param.ProductCreateParams.Type;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentMethodService extends BaseService<PaymentMethod, Long> {

	private final PaymentMethodRepo paymentMethodRepo;
	
	private final PaymentMapper paymentMapper;
	
	@Value("${stripe.secret}")
	private String stripeSecret;
	
	@PostConstruct
	public void init() {
		Stripe.apiKey=stripeSecret;
	}
	
	public PaymentMethodDto findPaymentMethodsByUserId(long id){
		PaymentMethod paymentMethod = paymentMethodRepo.findByUserId(id).orElseThrow(()->new NoPayemntMethodException("no payment method provided please add new one "));
		return paymentMapper.map(paymentMethod);
	}
	
	
	
	public PaymentIntent createPayemntIntent (UserDetailsImpl  user) throws StripeException {
		CustomerCreateParams params =
				  CustomerCreateParams.builder()
				  .setEmail(user.getEmail())
				  .setName(user.getName())
				    .build();

				Customer customer = Customer.create(params);
		
		PaymentIntentCreateParams params1 =
				  PaymentIntentCreateParams
				    .builder()
				    .setCustomer(customer.getId())
				    .setSetupFutureUsage(PaymentIntentCreateParams.SetupFutureUsage.OFF_SESSION)
				    .setAutomaticPaymentMethods(
				      PaymentIntentCreateParams.AutomaticPaymentMethods
				        .builder()
				        .setEnabled(true)
				        .build()
				    )
				    .build();
				PaymentIntent paymentIntent = PaymentIntent.create(params1);
		 return paymentIntent;
	}
}
