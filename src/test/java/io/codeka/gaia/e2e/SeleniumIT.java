package io.codeka.gaia.e2e;

import io.codeka.gaia.test.MongoContainer;
import io.percy.selenium.Percy;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Tag("e2e")
public class SeleniumIT {

    @LocalServerPort
    private int serverPort;

    @Container
    private static MongoContainer mongoContainer = new MongoContainer()
            .withScript("src/test/resources/db/00_team.js")
            .withScript("src/test/resources/db/10_user.js")
            .withScript("src/test/resources/db/20_module.js")
            .withScript("src/test/resources/db/30_stack.js")
            .withScript("src/test/resources/db/40_job.js")
            .withScript("src/test/resources/db/50_step.js")
            .withScript("src/test/resources/db/60_terraformState.js");

    private static WebDriver driver;

    private static Percy percy;

    @BeforeAll
    public static void openServerAndBrowser() throws IOException {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("-headless");
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments(
//                "--headless",
//                "--disable-web-security",
//                "--allow-running-insecure-content",
//                "--ignore-certificate-errors");
        driver = new FirefoxDriver(options);

        percy = new Percy(driver);

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterAll
    public static void closeServerAndBrowser() {
        // Close our test browser.
        driver.quit();
    }

    private String testUrl(){
        return "http://localhost:"+serverPort;
    }

    @Test
    @Order(1) // this test runs first as it logs the user in !
    void loginPage() {
        driver.get(testUrl());
        driver.manage().window().setSize(new Dimension(1280,800));

        var page = new LoginPage(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), page);

        percy.snapshot("Login Page");

        page.login("admin", "admin123");
    }

    @Test
    void dashboardPage_showsModuleCount() {
        driver.get(testUrl()+"/dashboard");

        var page = new DashboardPage(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), page);
        page.waitForPageLoaded();

        assertEquals(3, page.modulesCount());
        assertEquals(1, page.stacksCount());
        assertEquals(0, page.stacksToUpdateCount());

        percy.snapshot("Dashboard");
    }

    @Test
    void modulesPage_showsModules() {
        driver.get(testUrl()+"/modules");

        var page = new ModulesPage(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), page);
        page.waitForPageLoaded();

        assertEquals(3, page.modulesCount());

        percy.snapshot("Modules");
    }

    @Test
    void modulePage_showsModuleDetails() {
        driver.get(testUrl()+"/modules/e01f9925-a559-45a2-8a55-f93dc434c676");

        var page = new ModulePage(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), page);

        assertThat(page.moduleName()).isEqualTo("terraform-docker-mongo");
        assertThat(page.moduleDescription()).contains("A sample terraform");
        assertThat(page.terraformImageTag()).isEqualTo("0.11.14");

        percy.snapshot("Module Details");
    }

    @Test
    void stackPage_showsStackDetails() {
        driver.get(testUrl()+"/stacks/de28a01f-257a-448d-8e1b-00e4e3a41db2/edit");

        var page = new StackPage(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), page);

        assertThat(page.stackName()).isEqualTo("local-mongo");

        percy.snapshot("Stack Details");
    }

    @Test
    void jobPage_showsJobDetails() {
        driver.get(testUrl()+"/stacks/de28a01f-257a-448d-8e1b-00e4e3a41db2/jobs/5e856dc7-6bed-465f-abf1-02980206ab2a");

        var page = new JobPage(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), page);

        assertThat(page.jobDetailTitle()).isEqualTo("Job 5e856dc7-6bed-465f-abf1-02980206ab2a");

        percy.snapshot("Job Details");
    }

    void takeScreenshot() {
        var file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        System.out.println(file.getAbsolutePath());
        try {
            FileUtils.copyFileToDirectory(file, new File("./target"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
