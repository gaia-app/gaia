package io.gaia_app.e2e.pages

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy

class StackPage(webDriver: WebDriver) {

    init {
        if(! webDriver.currentUrl.contains("/stacks/")){
            throw IllegalStateException("This is not the stack page. Current page is ${webDriver.currentUrl}")
        }
    }

    @FindBy(id="stack.name")
    private lateinit var nameInput: WebElement

    fun stackName() : String = nameInput.getAttribute("value")

}
