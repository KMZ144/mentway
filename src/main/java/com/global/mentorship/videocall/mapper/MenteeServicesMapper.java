package com.global.mentorship.videocall.mapper;

import org.mapstruct.Mapper;

import com.global.mentorship.base.mapper.BaseMapper;
import com.global.mentorship.videocall.dto.MenteeServicesDto;
import com.global.mentorship.videocall.entity.MenteesServices;

@Mapper
public interface MenteeServicesMapper extends BaseMapper<MenteesServices, MenteeServicesDto> {

}
