package io.codeka.gaia.e2e;

import io.codeka.gaia.test.MongoContainer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.PageFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
@Testcontainers
public class SeleniumIT {

    @LocalServerPort
    private int serverPort;

    @Container
    private static MongoContainer mongoContainer = new MongoContainer()
            .withScript("src/test/resources/db/00_team.js")
            .withScript("src/test/resources/db/10_user.js")
            .withScript("src/test/resources/db/20_module.js");

    private static WebDriver driver;

//    private static Percy percy;

    @BeforeAll
    public static void openServerAndBrowser() throws IOException {
        FirefoxOptions options = new FirefoxOptions();
        driver = new FirefoxDriver(options);

//        ChromeOptions options = new ChromeOptions();
//        options.addArguments(
//                "--headless",
//                "--disable-web-security",
//                "--allow-running-insecure-content",
//                "--ignore-certificate-errors");
//        driver = new ChromeDriver(options);
//        percy = new Percy(driver);
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
    void loginPage_opens() throws Exception {
        driver.get(testUrl());
        LoginPage page = PageFactory.initElements(driver, LoginPage.class);
        page.login("admin", "admin123");
    }

    @Test
    void dashboardShows(){

    }
}
