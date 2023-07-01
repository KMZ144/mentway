package com.global.mentorship.payment.service;

import com.global.mentorship.base.service.BaseService;
import com.global.mentorship.error.NoPayemntMethodException;
import com.global.mentorship.error.PaymentMethodAlreadyExist;

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
import com.global.mentorship.user.entity.User;
import com.global.mentorship.user.service.UserService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.Refund;
import com.stripe.model.Token;
import com.stripe.net.RequestOptions;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.PaymentMethodCreateParams;
import com.stripe.param.PaymentMethodCreateParams.CardDetails;
import com.stripe.param.ProductCreateParams.Type;
import com.stripe.param.RefundCreateParams;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentMethodService extends BaseService<PaymentMethod, Long> {

	private final PaymentMethodRepo paymentMethodRepo;

	private final PaymentMapper paymentMapper;

	private final UserService userService;

	@Value("${stripe.secret}")
	private String stripeSecret;

	@PostConstruct
	public void init() {
		Stripe.apiKey = stripeSecret;
	}

	public PaymentMethodDto findPaymentMethodsByUserId(long id) {
		PaymentMethod paymentMethod = paymentMethodRepo.findByUserId(id)
				.orElseThrow(() -> new NoPayemntMethodException("no payment method provided please add new one "));
		return paymentMapper.map(paymentMethod);
	}
	
	public User setValidPayemntMethod(long id,String paymentIntentId) throws StripeException {
		User user =  userService.findById(id);
		user.setHasValidPayment(true);
		Refund refund =  refundWithPaymentIntent(paymentIntentId,100L);
		userService.update(user);
		return user;
	}

	public PaymentIntent createPayemntMethod(long id) throws StripeException {
		User user = userService.findById(id);
		if (user.getStripeId() != null) {
			throw new PaymentMethodAlreadyExist("payment method already exist");
		}
		Customer customer = createCustomer(user);
		PaymentIntent paymentIntent = createPayemntIntent(customer);
		return paymentIntent;
	}
	
	private Refund refundWithPaymentIntent(String paymentIntentId ,long amount) throws StripeException {
		 PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);
		 RefundCreateParams params =
				  RefundCreateParams.builder()
				  .setPaymentIntent(paymentIntentId)
				  .setAmount(amount)
				  .build();
				Refund refund = Refund.create(params);
				return refund;
	}

	private Customer createCustomer(User user) throws StripeException {
		CustomerCreateParams params = CustomerCreateParams.builder().setEmail(user.getEmail()).setName(user.getName())
				.build();
		Customer customer = Customer.create(params);
		user.setStripeId(customer.getId());
		userService.update(user);
		return customer;

	}

	private PaymentIntent createPayemntIntent(Customer customer) throws StripeException {
		PaymentIntentCreateParams params = PaymentIntentCreateParams.builder().setCustomer(customer.getId())
				.setSetupFutureUsage(PaymentIntentCreateParams.SetupFutureUsage.OFF_SESSION).setAmount(100L)
				.setCurrency("usd").setAutomaticPaymentMethods(
						PaymentIntentCreateParams.AutomaticPaymentMethods.builder().setEnabled(true).build())
				.build();
		PaymentIntent paymentIntent = PaymentIntent.create(params);
		return paymentIntent;

	}
}
