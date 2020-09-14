package io.gaia_app.e2e.stepDefs;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.gaia_app.e2e.pages.DashboardPage;
import io.gaia_app.e2e.pages.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DashboardStepDefs extends StepDefs {

    @Given("I go on the Gaia login page")
    public void i_go_on_the_gaia_login_page() {
        driver.get(baseUrl());

        var page = new LoginPage(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), page);
    }

    @Given("I login with user {string} and password {string}")
    public void i_login_onto_Gaia_with_user_and_password(String login, String password) {
        driver.get(baseUrl());

        // print html here !
        System.out.println(driver.findElement(By.tagName("body")).getAttribute("outerHTML"));

        var page = new LoginPage(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), page);

        page.login(login, password);
    }

    @Then("I arrive on the Dashboard page")
    public void i_arrive_on_the_dashboard_page() {
        var page = new DashboardPage(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), page);
        page.waitForPageLoaded();

        assertEquals(3, page.modulesCount());
        assertEquals(1, page.stacksCount());
        assertEquals(0, page.stacksToUpdateCount());
    }

}
