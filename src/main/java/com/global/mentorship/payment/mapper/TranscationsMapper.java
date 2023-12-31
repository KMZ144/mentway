package com.global.mentorship.payment.mapper;

import org.mapstruct.Mapper;

import com.global.mentorship.base.mapper.BaseMapper;
import com.global.mentorship.payment.dto.TranscationsDto;
import com.global.mentorship.payment.entity.Transcations;

@Mapper
public interface TranscationsMapper extends BaseMapper<Transcations, TranscationsDto> {

}
