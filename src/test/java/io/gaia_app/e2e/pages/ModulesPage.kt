package io.gaia_app.e2e.pages

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

class ModulesPage (private val webDriver: WebDriver){

    private lateinit var moduleCards: List<WebElement>

    init {
        if(! webDriver.currentUrl.endsWith("/modules")){
            throw IllegalStateException("This is not the modules page. Current page is ${webDriver.currentUrl}")
        }
    }

    fun modulesCount() = this.moduleCards.size

    fun waitForPageLoaded() {
        val wait = WebDriverWait(webDriver, Duration.ofSeconds(10))
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("moduleCard")))

        moduleCards = webDriver.findElements(By.className("moduleCard"))
    }

}
