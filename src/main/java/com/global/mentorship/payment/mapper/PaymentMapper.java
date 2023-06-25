package com.global.mentorship.payment.mapper;

import org.mapstruct.Mapper;

import com.global.mentorship.base.mapper.BaseMapper;
import com.global.mentorship.payment.dto.PaymentMethodDto;
import com.global.mentorship.payment.entity.PaymentMethod;

@Mapper
public interface PaymentMapper extends BaseMapper<PaymentMethod, PaymentMethodDto> {

}
