package com.pitangproject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.pitangproject.entity.Phone;
import com.pitangproject.entity.User;
import com.pitangproject.repository.PhoneRepository;
import com.pitangproject.repository.UserRepository;
import com.pitangproject.security.encoder.HashEncoder;

/**
 * @author Thiago Gitirana
 *
 */
@SpringBootApplication
public class PitangprojectApplication {

	private static final Logger log = LoggerFactory.getLogger(PitangprojectApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(PitangprojectApplication.class, args);
	}

	/**
	 * Metódo resposável por inserir usuários ao iniciar a aplicação
	 * 
	 * @param userRepository
	 * @return CommandLineRunner
	 */
	@Bean
	public CommandLineRunner demo(UserRepository userRepository, PhoneRepository phoneRepository) {
		return (args) -> {

			PasswordEncoder encoder = new HashEncoder(); 
			
			User user1 = new User();
			user1.setFirstName("Thiago");
			user1.setLastName("Gitirana");
			user1.setEmail("thiagogitirana@gmail.com");
			user1.setPassword(encoder.encode("senha123456"));
			user1.setPhones(new ArrayList<>());
			user1.addPhone(new Phone(996513747, 81, "+55"));
			user1.addPhone(new Phone(34467871, 81, "+55"));
			user1.setCreatedAt(new Date());
			user1.setLastLogin(new Date());
			userRepository.save(user1);

			User user2 = new User();
			user2.setFirstName("Maria");
			user2.setLastName("Silva");
			user2.setEmail("maria@gmail.com");
			user2.setPassword(encoder.encode("teszt321@#$!"));
			user2.setPhones(new ArrayList<>());
			user2.addPhone(new Phone(88556677, 11, "+55"));
			user2.addPhone(new Phone(44221133, 11, "+55"));
			user2.addPhone(new Phone(11111111, 11, "+55"));
			user2.addPhone(new Phone(32323232, 11, "+55"));
			user2.setCreatedAt(new Date());
			user2.setLastLogin(new Date());
			userRepository.save(user2);

			User user3 = new User();
			user3.setFirstName("João");
			user3.setLastName("Cruz");
			user3.setEmail("joao@gmail.com");
			user3.setPassword(encoder.encode("teszt321@#$!"));
			user3.setPhones(new ArrayList<>());
			user3.addPhone(new Phone(75315966, 21, "+55"));
			user3.addPhone(new Phone(33221100, 21, "+55"));
			user3.addPhone(new Phone(44554455, 21, "+55"));
			user3.setCreatedAt(new Date());
			user3.setLastLogin(new Date());
			userRepository.save(user3);

			log.info("Usuários cadastrados");
			for (User users : userRepository.findAll()) {
				StringBuilder builder = new StringBuilder();
				builder.append("Usuários: ").append(users.getFirstName()).append(" ").append(users.getLastName())
				.append(" E-Mail: ").append(users.getEmail()).append(" Pass: ").append(users.getPassword());
				log.info(builder.toString());
				log.info("Telefones: ");
				List<Phone> phones = phoneRepository.findByUser(users);
				for (Phone phone : phones) {
					builder = new StringBuilder();
					builder.append("Telefone: ").append(phone.getCountryCode()).append(" ").append(phone.getAreaCode())
							.append(" ").append(phone.getNumber());
					log.info(builder.toString());
				}
			}
		};
	}
}
