package io.gaia_app.e2e.stepDefs;

import org.openqa.selenium.WebDriver;
import org.springframework.boot.web.server.LocalServerPort;

public class StepDefs {

    public static WebDriver driver;

    @LocalServerPort
    private int serverPort;

    protected String baseUrl(){
        return "http://localhost:"+serverPort;
    }

}
