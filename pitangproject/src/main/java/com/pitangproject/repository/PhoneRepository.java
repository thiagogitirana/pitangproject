package com.pitangproject.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.pitangproject.entity.Phone;
import com.pitangproject.entity.User;

/**
 * @author Thiago Gitirana
 *
 */
@RepositoryRestResource(collectionResourceRel = "phone", path = "phone")
public interface PhoneRepository extends PagingAndSortingRepository<Phone, Long> {
	List<Phone> findByUser(@Param("user") User user);
}
