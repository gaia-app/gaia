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

	@Bean
	@Autowired
	CommandLineRunner cli(TerraformModuleRepository repository,
						  TerraformStateRepository terraformStateRepository,
						  StackRepository stackRepository){
		return args -> {
			repository.deleteAll();
			//terraformStateRepository.deleteAll();
			stackRepository.deleteAll();

			// create dummy module for tests
			repository.saveAll(List.of(buildDockerMongoModule()));

			var stack = new Stack();
			stack.setId("5a215b6b-fe53-4afa-85f0-a10175a7f264");
			stack.setName("mongo-instance-1");
			stack.setDescription("first instance of mongo module");
			stack.setModuleId("e01f9925-a559-45a2-8a55-f93dc434c676");
			stack.getVariableValues().put("mongo_container_name", "test");
			stack.getVariableValues().put("mongo_exposed_port", "27117");
			stack.setProviderSpec("provider \"docker\" {\n" +
					"    host = \"unix:///var/run/docker.sock\"\n" +
					"}");
			stackRepository.save(stack);

			var stack2 = new Stack();
			stack2.setId("143773fa-4c2e-4baf-a7fb-79d23e01c5ca");
			stack2.setName("mongo-instance-2");
			stack2.setDescription("second instance of mongo module");
			stack2.setModuleId("e01f9925-a559-45a2-8a55-f93dc434c676");
			stackRepository.save(stack2);
		};
	}

	private TerraformModule buildDockerMongoModule() {
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
		tvar.setEditable(true);

		var tvar2 = new TerraformVariable();
		tvar2.setName("mongo_exposed_port");
		tvar2.setDescription("the exposed port of the mongo container");
		tvar2.setDefaultValue("27017");
		tvar2.setEditable(true);

		module.setVariables(List.of(tvar, tvar2));
		return module;
	}

}
