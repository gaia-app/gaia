package io.gaia_app.e2e.pages

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.ExpectedConditions.titleIs
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

class UsersPage(private val webDriver: WebDriver) {

    private lateinit var usersRows: List<WebElement>

    init {
        val wait = WebDriverWait(webDriver, Duration.ofSeconds(10))
        wait.until(titleIs("Gaia - Users"))
    }

    fun usersCount() = usersRows.size

    fun waitForPageLoaded() {
        val wait = WebDriverWait(webDriver, Duration.ofSeconds(10))
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("usersTable")))

        val usersTable = webDriver.findElement(By.id("usersTable"))
        usersRows = usersTable.findElement(By.tagName("tbody")).findElements(By.tagName("tr"))
    }

}
