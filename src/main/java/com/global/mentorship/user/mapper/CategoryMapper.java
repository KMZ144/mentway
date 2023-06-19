package com.global.mentorship.user.mapper;

import org.mapstruct.Mapper;

import com.global.mentorship.base.mapper.BaseMapper;
import com.global.mentorship.user.dto.CategoryDto;
import com.global.mentorship.user.entity.Category;


@Mapper
public interface CategoryMapper extends BaseMapper<Category, CategoryDto> {

	
}
