package io.codeka.gaia.e2e

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy

class DashboardPage (val webDriver: WebDriver){

    init {
        if(! webDriver.title.equals("Gaia - A terraform UI")){
            throw IllegalStateException("This is not the dashboard page. Current page is ${webDriver.title}")
        }
    }

    @FindBy(className= "modules_count")
    private lateinit var modulesCountElement: WebElement

    @FindBy(className="stacks_count")
    private lateinit var stacksCountElement: WebElement

    @FindBy(className = "stacks_toUpdate_count")
    private lateinit var stacksToUpdateCountElement: WebElement

    fun modulesCount() = this.modulesCountElement.text.toInt()
    fun stacksCount() = this.stacksCountElement.text.toInt()
    fun stacksToUpdateCount() = this.stacksToUpdateCountElement.text.toInt()

}