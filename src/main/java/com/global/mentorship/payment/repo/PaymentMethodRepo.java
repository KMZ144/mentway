package com.global.mentorship.payment.repo;

import com.global.mentorship.base.repo.BaseRepo;
import com.global.mentorship.payment.entity.PaymentMethod;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository
public interface PaymentMethodRepo extends BaseRepo<PaymentMethod, Long> {

	Optional<PaymentMethod> findByUserId(long id);
}
