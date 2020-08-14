package io.gaia_app.e2e.stepDefs;

import io.cucumber.java.en.Then;
import io.percy.selenium.Percy;

public class PercyStepDef extends StepDefs{

    @Then("Percy takes a snapshot named {string}")
    public void percyTakesASnapshotNamedDashboardPage(String snapshotName) {
        new Percy(driver).snapshot(snapshotName);
    }
}
