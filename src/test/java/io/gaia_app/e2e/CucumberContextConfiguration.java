package io.gaia_app.e2e;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.gaia_app.test.SharedMongoContainerTest;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * This class is the glue between cucumber and spring
 * It is instantiated at the start of the cucumber tests (so only once)
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@io.cucumber.spring.CucumberContextConfiguration
public class CucumberContextConfiguration extends SharedMongoContainerTest {

    @Autowired
    private WebDriver driver;

    @Before
    public void setup_cucumber_spring_context() {
        mongo.emptyDatabase();
        mongo.runScript("00_organization.js");
        mongo.runScript("10_user.js");
        mongo.runScript("20_module.js");
        mongo.runScript("30_stack.js");
        mongo.runScript("40_job.js");
        mongo.runScript("50_step.js");
        mongo.runScript("60_terraformState.js");
        mongo.runScript("80_plan.js");
    }

    @After
    public void tearDown(){
        // login out by deleting all session cookies
        driver.manage().deleteAllCookies();
    }

    @TestConfiguration
    static class WebDriverConfiguration{

        @Bean(destroyMethod = "quit")
        WebDriver webDriver(){
            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("-headless");

            FirefoxDriver firefoxDriver = new FirefoxDriver(options);
            firefoxDriver.manage().window().setSize(new Dimension(1280,800));
            return firefoxDriver;
        }
    }

}
