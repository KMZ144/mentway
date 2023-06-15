package com.global.mentorship.base.repo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseRepo<T,ID>  extends JpaRepository<T, ID>{

}
