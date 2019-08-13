package io.codeka.gaia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class Gaia {

	public static void main(String[] args) {
		SpringApplication.run(Gaia.class, args);
	}

}
