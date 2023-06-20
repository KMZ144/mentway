package com.global.mentorship.videocall.mapper;

import org.mapstruct.Mapper;

import com.global.mentorship.base.mapper.BaseMapper;
import com.global.mentorship.videocall.dto.ServicesDto;
import com.global.mentorship.videocall.entity.Services;
@Mapper
public interface ServicesMapper extends BaseMapper<Services, ServicesDto> {

}
