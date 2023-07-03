package com.global.mentorship.payment.service;

import com.global.mentorship.base.service.BaseService;
import com.global.mentorship.error.NoPayemntMethodException;
import com.global.mentorship.notification.service.EmailService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.global.mentorship.payment.entity.PaymentMethod;
import com.global.mentorship.payment.entity.Transcations;
import com.global.mentorship.user.entity.Mentor;
import com.global.mentorship.user.entity.User;
import com.global.mentorship.user.service.UserService;
import com.global.mentorship.videocall.entity.MenteesServices;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import com.stripe.model.AccountLink;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethodCollection;
import com.stripe.model.Refund;
import com.stripe.model.Transfer;
import com.stripe.param.AccountCreateParams;
import com.stripe.param.AccountLinkCreateParams;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.PaymentMethodListParams;
import com.stripe.param.RefundCreateParams;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentMethodService extends BaseService<PaymentMethod, Long> {

	private final TranscationsService transcationsService;

	private final EmailService mailService;

	private final UserService userService;
	
	@Value("${stripe.secret}")
	private String stripeSecret;

	@PostConstruct
	public void init() {
		Stripe.apiKey = stripeSecret;
	}

	public void setValidPayemntMethod(long id, String paymentIntentId) throws StripeException {
		User user = userService.findById(id);
		if (user.getStripeId() == null) {
			throw new NoPayemntMethodException("No payment method added ");
		}
		user.setHasValidPayment(true);
		Refund refund = refundWithPaymentIntent(paymentIntentId, 100L);
		userService.update(user);
		mailService.sendEmail(user, "Payemnt Method Verification ", "Your Payment Method has successfully verfied");
	}

	public String validateMentorPayment(long id) throws StripeException {
		User user = userService.findById(id);
		String accountId = createAccountForMentor(user);
		String activateUrl = activateAccount(user, accountId);
		return activateUrl;
	}

	private String createAccountForMentor(User user) throws StripeException {
		if (user.getStripeId()!=null) {
			return user.getStripeId();
		}
		AccountCreateParams params = AccountCreateParams.builder().setEmail(user.getEmail())
				.setType(AccountCreateParams.Type.EXPRESS).build();
		Account account = Account.create(params);
		user.setStripeId(account.getId());
		userService.update(user);
		return account.getId();
	}

	private String activateAccount(User user, String accountId) throws StripeException {
		AccountLinkCreateParams params1 = AccountLinkCreateParams.builder().setAccount(accountId)
				.setRefreshUrl("http://localhost:4200").setReturnUrl("http://localhost:4200")
				.setType(AccountLinkCreateParams.Type.ACCOUNT_ONBOARDING).build();
		AccountLink accountLink = AccountLink.create(params1);
		user.setStripeId(accountId);
		user.setHasValidPayment(true);
		userService.update(user);
		return accountLink.getUrl();
	}
	
	public void transferFundsToMentor(MenteesServices menteesServices) throws StripeException {
		long amount = menteesServices.getServices().getPrice();
		Mentor mentor = menteesServices.getServices().getMentor();
		Map<String, Object> params1 = new HashMap<>();
		params1.put("amount", amount + 1000L);
		params1.put("currency", "usd");
		params1.put("source", "tok_bypassPending");

		Charge charge = Charge.create(params1);

		Map<String, Object> params = new HashMap<>();
		params.put("amount", amount);
		params.put("currency", "usd");
		params.put("destination", mentor.getStripeId());
		params.put("transfer_group", "ORDER_95");

		Transfer transfer = Transfer.create(params);
		Transcations transcation = new Transcations();
		transcation.setPaymentIntentId(transfer.getId());
		transcation.setUser(mentor);
		transcationsService.insert(transcation);

		mailService.sendEmail(mentor, "money has transfered", "A trsnfer of amount " + amount + "$ has moved to your account");
	}



	public void payForService(String customerId, long amount) throws StripeException {

		PaymentMethodCollection paymentMethodCollection = getPaymentMethodsWithCustomer(customerId);
		String paymentMethodId = paymentMethodCollection.getData().get(0).getId();
		PaymentIntent paymentIntent = chargeCustomer(customerId, paymentMethodId, amount);
	}

	private PaymentIntent chargeCustomer(String customerId, String PaymentMethodId, long amount)
			throws StripeException {

		PaymentIntentCreateParams params = PaymentIntentCreateParams.builder().setCurrency("usd")
				.setAmount(amount * 100)
				.setAutomaticPaymentMethods(
						PaymentIntentCreateParams.AutomaticPaymentMethods.builder().setEnabled(true).build())
				.setPaymentMethod(PaymentMethodId).setCustomer(customerId).setReturnUrl("http://locahost:4200")
				.setConfirm(true).setReturnUrl("http://localhost:4200").setOffSession(true)

				.build();

		PaymentIntent paymentIntent = PaymentIntent.create(params);
		return paymentIntent;

	}

	private PaymentMethodCollection getPaymentMethodsWithCustomer(String customerId) throws StripeException {

		PaymentMethodListParams params = PaymentMethodListParams.builder().setCustomer(customerId)
				.setType(PaymentMethodListParams.Type.CARD).build();
		PaymentMethodCollection paymentMethods = com.stripe.model.PaymentMethod.list(params);
		return paymentMethods;
	}

	public PaymentIntent createPayemntMethod(long id) throws StripeException {
		User user = userService.findById(id);
		String customerId ;
		if ((user.getStripeId()==null)) {
			customerId = createCustomer(user); 
			user.setStripeId(customerId);
			userService.update(user);
		}
		else {
			customerId = user.getStripeId();
		}
		PaymentIntent paymentIntent = createPayemntIntent(customerId);
		Transcations transcations = new Transcations();
		transcations.setPaymentIntentId(paymentIntent.getId());
		transcations.setUser(user);
		transcationsService.insert(transcations);
		return paymentIntent;
	}

	private String createCustomer(User user) throws StripeException {
		CustomerCreateParams params = CustomerCreateParams.builder().setEmail(user.getEmail()).setName(user.getName())
				.build();
		Customer customer = Customer.create(params);
		user.setStripeId(customer.getId());
		userService.update(user);
		return customer.getId();

	}

	private PaymentIntent createPayemntIntent(String CustomerId) throws StripeException {
		PaymentIntentCreateParams params = PaymentIntentCreateParams.builder().setCustomer(CustomerId)
				.setSetupFutureUsage(PaymentIntentCreateParams.SetupFutureUsage.OFF_SESSION).setAmount(100L)
				.setCurrency("usd").setAutomaticPaymentMethods(
						PaymentIntentCreateParams.AutomaticPaymentMethods.builder().setEnabled(true).build())
				.build();
		PaymentIntent paymentIntent = PaymentIntent.create(params);
		return paymentIntent;

	}

	private Refund refundWithPaymentIntent(String paymentIntentId, long amount) throws StripeException {
		PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);
		RefundCreateParams params = RefundCreateParams.builder().setPaymentIntent(paymentIntentId).setAmount(amount)
				.build();
		Refund refund = Refund.create(params);
		return refund;
	}
	
//	public void setMAnual(String accId) throws StripeException {
//	Account resource = Account.retrieve(accId);
//	AccountUpdateParams params2 = AccountUpdateParams.builder().setSettings(AccountUpdateParams.Settings.builder()
//			.setPayouts(AccountUpdateParams.Settings.Payouts.builder()
//					.setSchedule(AccountUpdateParams.Settings.Payouts.Schedule.builder()
//							.setInterval(AccountUpdateParams.Settings.Payouts.Schedule.Interval.MANUAL).build())
//					.build())
//			.build()).build();
//
//	Account account2 = resource.update(params2);
//
//}

}
