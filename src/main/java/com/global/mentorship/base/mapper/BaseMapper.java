package com.global.mentorship.base.mapper;

public interface BaseMapper <T,DTO> {

	DTO map (T entity);
	T unMap (DTO entity);
	Iterable<DTO> map (Iterable<T> entities );
	Iterable<T> unMap (Iterable<DTO> DTOs );
}
