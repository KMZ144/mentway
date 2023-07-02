package com.global.mentorship.payment.service;

import com.global.mentorship.base.service.BaseService;
import com.global.mentorship.error.NoPayemntMethodException;
import com.global.mentorship.error.NotValidPaymentException;
import com.global.mentorship.error.PaymentMethodAlreadyExist;
import com.global.mentorship.notification.service.EmailService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.global.mentorship.payment.dto.PaymentMethodDto;
import com.global.mentorship.payment.entity.PaymentMethod;
import com.global.mentorship.payment.entity.Transcations;
import com.global.mentorship.payment.mapper.PaymentMapper;
import com.global.mentorship.payment.repo.PaymentMethodRepo;
import com.global.mentorship.security.dto.UserDetailsImpl;
import com.global.mentorship.user.entity.Mentor;
import com.global.mentorship.user.entity.User;
import com.global.mentorship.user.service.UserService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethodCollection;
import com.stripe.model.Refund;
import com.stripe.model.Token;
import com.stripe.net.RequestOptions;
import com.stripe.param.AccountCreateParams;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.PaymentMethodCreateParams;
import com.stripe.param.PaymentMethodCreateParams.CardDetails;
import com.stripe.param.PaymentMethodListParams;
import com.stripe.param.ProductCreateParams.Type;
import com.stripe.param.RefundCreateParams;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentMethodService extends BaseService<PaymentMethod, Long> {

	private final PaymentMethodRepo paymentMethodRepo;

	private final PaymentMapper paymentMapper;
	
	private final TranscationsService transcationsService;

	private final EmailService mailService;

	private final UserService userService;

	@Value("${stripe.secret}")
	private String stripeSecret;

	@PostConstruct
	public void init() {
		Stripe.apiKey = stripeSecret;
	}

	
	public void setValidPayemntMethod(long id,String paymentIntentId) throws StripeException {
		User user =  userService.findById(id);
		if (user.getStripeId()==null) {
			throw new NoPayemntMethodException("No payment method added ");
		}
		user.setHasValidPayment(true);
		Refund refund =  refundWithPaymentIntent(paymentIntentId,100L);
		userService.update(user);
		mailService.sendEmail(user, 
				"Payemnt Method Verification ",
				"Your Payment Method has successfully verfied");
		
	}
	
	public void createPaymentMethod(Mentor mentor) throws StripeException {
		AccountCreateParams params =
				  AccountCreateParams.builder()
				  .setType(AccountCreateParams.Type.EXPRESS)
				  .setEmail(mentor.getEmail())
				  .build();

				Account account = Account.create(params);
				User user = userService.findById(mentor.getId());
				user.setStripeId(account.getId());
	}
	
	public void payMentorForService(String customerId,long amount) {
		
	}
	
	public void payForService(String customerId,long amount) throws StripeException {
	
		PaymentMethodCollection paymentMethodCollection = getPaymentMethodsWithCustomer(customerId);
		String paymentMethodId = paymentMethodCollection.getData().get(0).getId();
		PaymentIntent paymentIntent = chargeCustomer(customerId,paymentMethodId,amount ); 
		
	}
	
	private PaymentIntent chargeCustomer (String customerId,String PaymentMethodId, long amount) throws StripeException {
		
		PaymentIntentCreateParams params =
				  PaymentIntentCreateParams.builder()
				    .setCurrency("usd")
				    .setAmount(amount)
				    .setAutomaticPaymentMethods(
				      PaymentIntentCreateParams.AutomaticPaymentMethods.builder().setEnabled(true).build()
				    )
				    .setPaymentMethod(PaymentMethodId)
				    .setCustomer(customerId)
				    .setConfirm(true)
				    .setOffSession(true)
				    .build();
				
				 PaymentIntent paymentIntent =  PaymentIntent.create(params);
				return paymentIntent;
				

	}
	
	private PaymentMethodCollection getPaymentMethodsWithCustomer(String customerId) throws StripeException {
	
		PaymentMethodListParams params =
				  PaymentMethodListParams.builder()
				    .setCustomer(customerId)
				    .setType(PaymentMethodListParams.Type.CARD)
				    .build();
				PaymentMethodCollection paymentMethods = com.stripe.model.PaymentMethod.list(params);
				return paymentMethods;
	}
	

	public PaymentIntent createPayemntMethod(long id) throws StripeException {
		User user = userService.findById(id);
//		if ((user.getStripeId()!=null)) {
//			transcationsService.findById(null)
//		}
		Customer customer = createCustomer(user);
		PaymentIntent paymentIntent = createPayemntIntent(customer);
		Transcations transcations = new Transcations();
		transcations.setPaymentIntentId(paymentIntent.getId());
		transcations.setUser(user);
		transcationsService.insert(transcations);
		return paymentIntent;
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
}
