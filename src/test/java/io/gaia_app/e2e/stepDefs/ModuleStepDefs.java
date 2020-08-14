package io.gaia_app.e2e.stepDefs;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.gaia_app.e2e.pages.*;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ModuleStepDefs extends StepDefs {

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

    @When("I go on the modules page")
    public void i_go_on_the_modules_page() {
        driver.get(baseUrl()+"/modules");

        var page = new ModulesPage(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), page);
        page.waitForPageLoaded();

        assertEquals(3, page.modulesCount());
    }

    @When("I go on the module {string} page")
    public void iGoOnTheModulePage(String moduleId) {
        driver.get(baseUrl()+"/modules/" + moduleId);

        var page = new ModulePage(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), page);

        assertThat(page.moduleName()).isEqualTo("terraform-docker-mongo");
        assertThat(page.moduleDescription()).contains("A sample terraform");
        assertThat(page.terraformImageTag()).isEqualTo("0.11.14");
    }

    @When("I go on the stack {string} page")
    public void iGoOnTheStackPage(String stackId) {
        driver.get(baseUrl()+"/stacks/"+stackId+"/edit");

        var page = new StackPage(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), page);

        assertThat(page.stackName()).isEqualTo("local-mongo");
    }

    @When("I go on the job {string} for stack {string} page")
    public void iGoOnTheJobForStackPage(String jobId, String stackId) {
        driver.get(baseUrl()+"/stacks/"+stackId+"/jobs/"+jobId);

        var page = new JobPage(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), page);

        assertThat(page.jobDetailTitle()).isEqualTo("Job " + jobId);
    }

}
