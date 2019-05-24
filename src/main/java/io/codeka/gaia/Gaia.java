package io.codeka.gaia;

import io.codeka.gaia.bo.TerraformModule;
import io.codeka.gaia.bo.TerraformVariable;
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

	@Bean
	@Autowired
	CommandLineRunner cli(TerraformModuleRepository repository, TerraformStateRepository terraformStateRepository){
		return args -> {
			repository.deleteAll();
			terraformStateRepository.deleteAll();

			// create dummy module for tests
			var module = new TerraformModule();
			module.setId("e01f9925-a559-45a2-8a55-f93dc434c676");
			module.setName("terraform-docker-mongo");
			module.setDescription("A sample terraform \uD83C\uDF0D module for running a mongodb \uD83C\uDF43 database inside a docker \uD83D\uDC33 container");
			module.setGitRepositoryUrl("https://github.com/juwit/terraform-docker-mongo.git");
			module.setDirectory("");
			module.setGitBranch("master");

			var tvar = new TerraformVariable();
			tvar.setName("mongo_container_name");
			tvar.setDescription("the name of the docker container");

			var tvar2 = new TerraformVariable();
			tvar2.setName("mongo_exposed_port");
			tvar2.setDescription("the exposed port of the mongo container");
			tvar2.setDefaultValue("27017");

			module.setVariables(List.of(tvar, tvar2));

			repository.saveAll(List.of(module));
		};
	}

}
