package com.pitangproject.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.pitangproject.entity.Phone;
import com.pitangproject.entity.User;

/**
 * 
 * @author Thiago Gitirana
 *
 */
public interface PhoneRepository extends CrudRepository<Phone, Long> {
	List<Phone> findByUser(User user);
}
