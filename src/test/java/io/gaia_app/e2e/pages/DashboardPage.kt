package io.gaia_app.e2e.pages

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

class DashboardPage (private val webDriver: WebDriver){

    init {
        if(! webDriver.currentUrl.endsWith("/dashboard")){
            throw IllegalStateException("This is not the dashboard page. Current page is ${webDriver.title}")
        }
    }

    fun waitForPageLoaded() {
        val wait = WebDriverWait(webDriver, Duration.ofSeconds(10))
        wait.until(ExpectedConditions.titleIs("Gaia - Dashboard"))
        wait.until(ExpectedConditions.numberOfElementsToBe(By.className("b-overlay"), 0))

        // getting widget values
        wait.until(ExpectedConditions.numberOfElementsToBe(By.className("widget-value"), 3))

        val countElements = webDriver.findElements(By.className("widget-value"))
        modulesCountElement = countElements[0]
        stacksCountElement = countElements[1]
        stacksToUpdateCountElement = countElements[2]
    }

    private lateinit var modulesCountElement: WebElement

    private lateinit var stacksCountElement: WebElement

    private lateinit var stacksToUpdateCountElement: WebElement

    fun modulesCount() = this.modulesCountElement.text.toInt()
    fun stacksCount() = this.stacksCountElement.text.toInt()
    fun stacksToUpdateCount() = this.stacksToUpdateCountElement.text.toInt()

}
