package com.global.mentorship.base.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;


@NoRepositoryBean
public interface BaseRepo<T,ID>  extends JpaRepository<T, ID>{

}
