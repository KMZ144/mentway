package com.global.mentorship.base.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.global.mentorship.base.entity.BaseEntity;
import com.global.mentorship.base.repo.BaseRepo;
import java.util.List;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseService<T extends BaseEntity<ID>,ID extends Number> {
	
	@Autowired
	private BaseRepo<T,ID> baseRepo;
	
	public List<T> findAll() {
		return baseRepo.findAll();
	}
	
	public T findById(ID id ) {
		return baseRepo.findById(id).orElseThrow(()->new EntityNotFoundException());
	}
	
	public T insert(T entity) {
		return baseRepo.save(entity);
	}
	
	public T update(T entity) {
		findById(entity.getId());
		return baseRepo.save(entity);
	}
	
	public void delete(T entity) {
		T entityFound= baseRepo.findById(entity.getId()).orElseThrow(()->new EntityNotFoundException());
		entityFound.setDeleted(true);
	}
	
}
