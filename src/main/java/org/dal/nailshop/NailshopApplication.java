package org.dal.nailshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class NailshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(NailshopApplication.class, args);
	}

}
