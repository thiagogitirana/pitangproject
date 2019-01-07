package com.pitangproject.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.pitangproject.entity.User;

/**
 * @author Thiago Gitirana
 *
 */
public interface UserRepository extends CrudRepository<User, Long> {

	List<User> findByLastName(String name);

	User findByEmail(String email);

}
