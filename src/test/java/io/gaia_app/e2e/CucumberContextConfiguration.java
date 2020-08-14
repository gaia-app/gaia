package io.gaia_app.e2e;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.gaia_app.e2e.stepDefs.StepDefs;
import io.gaia_app.test.SharedMongoContainerTest;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CucumberContextConfiguration extends SharedMongoContainerTest {

    @Before
    public void setup_cucumber_spring_context() {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("-headless");

        FirefoxDriver firefoxDriver = new FirefoxDriver(options);
        firefoxDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        firefoxDriver.manage().window().setSize(new Dimension(1280,800));

        StepDefs.driver = firefoxDriver;

        mongo.emptyDatabase();
        mongo.runScript("src/test/resources/db/00_team.js");
        mongo.runScript("src/test/resources/db/10_user.js");
        mongo.runScript("src/test/resources/db/20_module.js");
        mongo.runScript("src/test/resources/db/30_stack.js");
        mongo.runScript("src/test/resources/db/40_job.js");
        mongo.runScript("src/test/resources/db/50_step.js");
        mongo.runScript("src/test/resources/db/60_terraformState.js");
    }

    @After
    public void tearDown(){
        StepDefs.driver.close();
    }

}
