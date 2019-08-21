package io.codeka.gaia.dashboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.info.InfoEndpoint;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Map;

@ControllerAdvice
public class DashboardControllerAdvice {

    @Autowired
    private InfoEndpoint infoEndpoint;

    /**
     * Exposing spring-boot build information as a model
     * @return
     */
    @ModelAttribute("info")
    public Map<String, Object> buildInformation(){
        return infoEndpoint.info();
    }
}
