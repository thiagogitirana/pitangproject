package com.pitangproject;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.pitangproject.entity.Phone;
import com.pitangproject.entity.User;
import com.pitangproject.repository.UserRepository;

@SpringBootApplication
public class PitangprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(PitangprojectApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner demo(UserRepository repository) {
		return (args) ->{
			User user = new User();
			user.setFirstName("Thiago");
			user.setLastName("Gitirana");
			user.setEmail("thiagogitirana@gmail.com");
			user.setPassword("123456");
			
			Phone celPhone = new Phone();
			celPhone.setNumber(996513747);
			celPhone.setCountryCode("+55");
			celPhone.setAreaCode(81);
			
			List<Phone> phones = new ArrayList<>();
			phones.add(celPhone);
			
			user.setPhones(phones);
			
			repository.save(user);
		};
	}
}

