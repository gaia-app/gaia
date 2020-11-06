package io.gaia_app.e2e.stepDefs;

import io.cucumber.java.en.When;
import io.gaia_app.e2e.pages.JobPage;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import static org.assertj.core.api.Assertions.assertThat;

public class UsersStepDefs extends StepDefs {

    @When("I go on the users page")
    public void iGoOnTheUsersPage() {
        driver.get(baseUrl()+"/users");
    }
}
