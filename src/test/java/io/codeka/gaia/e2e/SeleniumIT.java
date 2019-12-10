package io.codeka.gaia.e2e;

import io.codeka.gaia.test.MongoContainer;
import io.percy.selenium.Percy;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
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
            .withScript("src/test/resources/db/20_module.js");

    private static WebDriver driver;

    private static Percy percy;

    @BeforeAll
    public static void openServerAndBrowser() throws IOException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments(
                "--headless",
                "--disable-web-security",
                "--allow-running-insecure-content",
                "--ignore-certificate-errors");
        driver = new ChromeDriver(options);

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
    void loginPage() throws IOException {
        driver.get(testUrl());
        driver.manage().window().setSize(new Dimension(1280,800));

        LoginPage page = PageFactory.initElements(driver, LoginPage.class);

        percy.snapshot("Login Page");

        page.login("admin", "admin123");
    }

    @Test
    void dashboardPage_showsModuleCount() throws IOException {
        driver.get(testUrl()+"/");

        var page = PageFactory.initElements(driver, DashboardPage.class);

        assertEquals(3, page.modulesCount());
        assertEquals(0, page.stacksCount());
        assertEquals(0, page.stacksToUpdateCount());

        percy.snapshot("Dashboard");
    }

    @Test
    void modulesPage_showsModules() throws IOException {
        driver.get(testUrl()+"/modules");

        var page = PageFactory.initElements(driver, ModulesPage.class);
        assertEquals(3, page.modulesCount());

        percy.snapshot("Modules");
    }

    @Test
    void modulePage_showsModuleDetails() throws IOException {
        driver.get(testUrl()+"/modules/e01f9925-a559-45a2-8a55-f93dc434c676");

        var page = new ModulePage(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), page);

        assertThat(page.moduleName()).isEqualTo("terraform-docker-mongo");
        assertThat(page.moduleDescription()).contains("A sample terraform");
        assertThat(page.cliVersion()).isEqualTo("0.11.14");

        percy.snapshot("Module Details");
    }

    void takeScreenshot() throws IOException {
        var file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        System.out.println(file.getAbsolutePath());
        FileUtils.copyFileToDirectory(file, new File("./target"));
    }
}
