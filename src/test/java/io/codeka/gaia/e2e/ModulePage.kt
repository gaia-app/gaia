package io.codeka.gaia.e2e

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory

class ModulePage(webDriver: WebDriver) {

    init {
        if(! webDriver.currentUrl.contains("/modules/")){
            throw IllegalStateException("This is not the module page. Current page is ${webDriver.currentUrl}")
        }
    }

    @FindBy(id="module.name")
    private lateinit var nameInput: WebElement

    @FindBy(id="module.cliVersion")
    private lateinit var cliVersionInput: WebElement

    @FindBy(id="module.description")
    private lateinit var descriptionInput: WebElement

    fun moduleName() : String = nameInput.getAttribute("value")

    fun moduleDescription() : String = descriptionInput.getAttribute("value")

    fun cliVersion() : String = cliVersionInput.getAttribute("value")

}