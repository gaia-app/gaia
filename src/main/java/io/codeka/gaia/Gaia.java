package io.codeka.gaia;

import io.codeka.gaia.bo.Stack;
import io.codeka.gaia.bo.TerraformModule;
import io.codeka.gaia.bo.TerraformVariable;
import io.codeka.gaia.repository.StackRepository;
import io.codeka.gaia.repository.TerraformModuleRepository;
import io.codeka.gaia.repository.TerraformStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;

@SpringBootApplication
@EnableMongoRepositories
public class Gaia {

	public static void main(String[] args) {
		SpringApplication.run(Gaia.class, args);
	}

}
