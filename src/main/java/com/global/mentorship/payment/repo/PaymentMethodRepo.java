package com.global.mentorship.payment.repo;

import com.global.mentorship.base.repo.BaseRepo;
import com.global.mentorship.payment.entity.PaymentMethod;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface PaymentMethodRepo extends BaseRepo<PaymentMethod, Long> {

	List<PaymentMethod> findByUserId(long id);
}
