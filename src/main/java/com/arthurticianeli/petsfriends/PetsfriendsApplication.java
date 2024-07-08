package com.arthurticianeli.petsfriends;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableJpaAuditing
public class PetsfriendsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetsfriendsApplication.class, args);
	}

}
