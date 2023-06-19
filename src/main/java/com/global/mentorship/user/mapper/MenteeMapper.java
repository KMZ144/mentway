package com.global.mentorship.user.mapper;

import org.mapstruct.Mapper;

import com.global.mentorship.base.mapper.BaseMapper;
import com.global.mentorship.user.dto.MenteeDto;
import com.global.mentorship.user.entity.Mentee;

@Mapper
public interface MenteeMapper extends BaseMapper<Mentee, MenteeDto> {

	
}
