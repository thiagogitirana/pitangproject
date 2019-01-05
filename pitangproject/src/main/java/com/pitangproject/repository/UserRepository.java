package com.pitangproject.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.pitangproject.entity.User;

/**
 * @author Thiago Gitirana
 *
 */
//@RepositoryRestResource(collectionResourceRel = "user", path = "user")
public interface UserRepository extends CrudRepository<User, Long> {

	List<User> findByLastName(@Param("name") String name);
	
}
