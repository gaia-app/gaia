package io.codeka.gaia;

import io.codeka.gaia.bo.TerraformModule;
import io.codeka.gaia.bo.TerraformVariable;
import io.codeka.gaia.repository.TerraformModuleRepository;
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
	CommandLineRunner cli(@Autowired TerraformModuleRepository repository){
		return args -> {
			// create dummy module for tests
			var module = new TerraformModule();
			module.setId("e01f9925-a559-45a2-8a55-f93dc434c676");
			module.setName("test 1");
			module.setDescription("a dummy module");
			module.setGitRepositoryUrl("https://github.com/dummy");
			module.setDirectory("dummy");
			module.setGitBranch("master");

			var tvar = new TerraformVariable();
			tvar.setName("dummy_var");
			tvar.setDescription("a dummy var description");
			tvar.setDefaultValue("a dummy value");

			module.setVariables(List.of(tvar));

			repository.deleteAll();
			repository.saveAll(List.of(module));
		};
	}

}
