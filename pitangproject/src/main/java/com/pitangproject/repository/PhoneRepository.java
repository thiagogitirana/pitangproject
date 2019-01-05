package com.pitangproject.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.pitangproject.entity.Phone;
import com.pitangproject.entity.User;

/**
 * @author Thiago Gitirana
 *
 */
//@RepositoryRestResource(collectionResourceRel = "phone", path = "phone")
public interface PhoneRepository extends CrudRepository<Phone, Long> {
	List<Phone> findByUser(@Param("user") User user);
}
