package io.gaia_app.e2e.stepDefs;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.gaia_app.e2e.pages.UsersPage;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import static org.assertj.core.api.Assertions.assertThat;

public class UsersStepDefs extends StepDefs {

    UsersPage page;

    @When("I go on the users page")
    public void iGoOnTheUsersPage() {
        driver.get(baseUrl()+"/users");

        page = new UsersPage(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), page);
        page.waitForPageLoaded();
    }

    @Then("I can see {int} users")
    public void iCanSeeUsers(int expectedUsersCount) {
        assertThat(page.usersCount()).isEqualTo(expectedUsersCount);
    }
}
