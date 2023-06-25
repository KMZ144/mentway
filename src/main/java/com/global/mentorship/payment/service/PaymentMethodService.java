package com.global.mentorship.payment.service;

import com.global.mentorship.base.service.BaseService;
import java.util.List;

import org.springframework.stereotype.Service;

import com.global.mentorship.payment.dto.PaymentMethodDto;
import com.global.mentorship.payment.entity.PaymentMethod;
import com.global.mentorship.payment.mapper.PaymentMapper;
import com.global.mentorship.payment.repo.PaymentMethodRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentMethodService extends BaseService<PaymentMethod, Long> {

	private final PaymentMethodRepo paymentMethodRepo;
	
	private final PaymentMapper paymentMapper;
	
	public List<PaymentMethodDto> findPaymentMethodsByUserId(long id){
		return paymentMapper.map(paymentMethodRepo.findByUserId(id));
	}
}
