package io.gaia_app.e2e.stepDefs;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;

public class StepDefs {

    @Autowired
    protected WebDriver driver;

    @LocalServerPort
    private int serverPort;

    protected String baseUrl(){
        return "http://localhost:"+serverPort;
    }

}
