package io.gaia_app.e2e.stepDefs;

import io.cucumber.java.en.When;
import io.gaia_app.e2e.pages.JobPage;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import static org.assertj.core.api.Assertions.assertThat;

public class JobStepDefs extends StepDefs {

    @When("I go on the job {string} for stack {string} page")
    public void iGoOnTheJobForStackPage(String jobId, String stackId) {
        driver.get(baseUrl()+"/stacks/"+stackId+"/jobs/"+jobId);

        var page = new JobPage(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), page);

        assertThat(page.jobDetailTitle()).isEqualTo("Job " + jobId);
    }

}
