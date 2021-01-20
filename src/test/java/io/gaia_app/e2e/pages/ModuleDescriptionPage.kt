package io.gaia_app.e2e.pages

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy

class ModuleDescriptionPage(webDriver: WebDriver) {

    init {
        if(! webDriver.currentUrl.contains("/description")){
            throw IllegalStateException("This is not the module description page. Current page is ${webDriver.currentUrl}")
        }
    }

    @FindBy(className= "markdown-body")
    private lateinit var readmeContent: WebElement

    fun readmeText() : String = readmeContent.text

}
