package com.global.mentorship.payment.repo;

import org.springframework.stereotype.Repository;

import com.global.mentorship.base.repo.BaseRepo;
import com.global.mentorship.payment.entity.Transcations;

@Repository
public interface TranscationsRepo extends BaseRepo<Transcations, Long> {

}
