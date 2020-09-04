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

    @When("I go on the modules page")
    public void i_go_on_the_modules_page() {
        driver.get(baseUrl()+"/modules");

        var page = new ModulesPage(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), page);
        page.waitForPageLoaded();

        assertEquals(3, page.modulesCount());
    }

    @When("I go on the module {string} page")
    public void i_go_on_a_module_page(String moduleId) {
        driver.get(baseUrl()+"/modules/" + moduleId);

        var page = new ModulePage(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), page);

        assertThat(page.moduleName()).isEqualTo("terraform-docker-mongo");
        assertThat(page.moduleDescription()).contains("A sample terraform");
        assertThat(page.terraformImageTag()).isEqualTo("0.11.14");
    }

}
