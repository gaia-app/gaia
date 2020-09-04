package io.gaia_app.e2e.stepDefs;

import io.cucumber.java.en.When;
import io.gaia_app.e2e.pages.StackPage;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import static org.assertj.core.api.Assertions.assertThat;

public class StackStepDefs extends StepDefs {

    @When("I go on the stack {string} page")
    public void iGoOnTheStackPage(String stackId) {
        driver.get(baseUrl()+"/stacks/"+stackId+"/edit");

        var page = new StackPage(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), page);

        assertThat(page.stackName()).isEqualTo("local-mongo");
    }

}
