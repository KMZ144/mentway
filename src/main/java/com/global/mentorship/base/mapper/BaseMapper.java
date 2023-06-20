
package com.global.mentorship.base.mapper;
import java.util.List;


public interface BaseMapper <T,DTO> {

	
	DTO map (T entity);
	T unMap (DTO entity);
	List<DTO> map (List<T> entities );
	List<T> unMap (List<DTO> DTOs );
}
