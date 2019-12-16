package io.codeka.gaia.e2e

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindAll
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.FindBys

class ModulesPage (webDriver: WebDriver){

    private var moduleCards: List<WebElement>

    init {
        if(! webDriver.title.equals("Gaia - Modules")){
            throw IllegalStateException("This is not the modules page. Current page is ${webDriver.title}")
        }
        moduleCards = webDriver.findElements(By.className("card"));
    }

    fun modulesCount() = this.moduleCards.size

}