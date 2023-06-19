package com.global.mentorship.user.mapper;


import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import com.global.mentorship.base.mapper.BaseMapper;
import com.global.mentorship.user.dto.MentorDto;
import com.global.mentorship.user.entity.Mentor;

@Mapper(componentModel = "spring")
public interface MentorMapper extends BaseMapper<Mentor, MentorDto> {

	
	  default Page<MentorDto> map(Page<Mentor> page) {
	        return page.map(this::map);
	    }
}
