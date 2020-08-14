package io.gaia_app.e2e.pages

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy

class ModulePage(webDriver: WebDriver) {

    init {
        if(! webDriver.currentUrl.contains("/modules/")){
            throw IllegalStateException("This is not the module page. Current page is ${webDriver.currentUrl}")
        }
    }

    @FindBy(id="module.name")
    private lateinit var nameInput: WebElement

    @FindBy(id="image_tag")
    private lateinit var terraformImageTag: WebElement

    @FindBy(id="module.description")
    private lateinit var descriptionInput: WebElement

    fun moduleName() : String = nameInput.getAttribute("value")

    fun moduleDescription() : String = descriptionInput.getAttribute("value")

    fun terraformImageTag() : String = terraformImageTag.getAttribute("value")

}
