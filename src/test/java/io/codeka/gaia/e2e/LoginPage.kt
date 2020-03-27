package io.codeka.gaia.e2e

import org.junit.jupiter.api.Assertions.assertEquals
import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.ExpectedConditions.*
import org.openqa.selenium.support.ui.WebDriverWait

class LoginPage(val webDriver: WebDriver) {

    init {
        val wait = WebDriverWait(webDriver, 10)
        wait.until { titleIs("Gaia - A terraform UI - Login") }
    }

    @FindBy(name="username")
    lateinit var loginInput: WebElement

    @FindBy(name="password")
    lateinit var passwordInput: WebElement

    @FindBy(tagName = "button")
    lateinit var submitButton: WebElement

    fun login(login: String, password: String){
        loginInput.sendKeys(login)
        passwordInput.sendKeys(password)
        submitButton.click()

        // wait for successful login
        val wait = WebDriverWait(webDriver, 10)
        wait.until { titleIs("Gaia - A terraform UI").andThen { elementToBeClickable(By.id("user.name")) } }

        assertEquals("admin", webDriver.findElement(By.id("user.name")).text);
    }

}
