package io.gaia_app.e2e;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.jupiter.api.Tag;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = "pretty", features = "src/test/features")
@Tag("e2e")
public class CucumberIT {

    // ces TU sont exécutés avec JUnit 4, et ne sont donc pas filtrés par maven avec le tag

}
