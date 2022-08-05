package io.gaia_app.e2e.pages

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.ui.ExpectedConditions.titleIs
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

class LoginPage(private val webDriver: WebDriver) {

    init {
        val wait = WebDriverWait(webDriver, Duration.ofSeconds(10))
        wait.until(titleIs("Gaia - A terraform UI - Login"))
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
        val wait = WebDriverWait(webDriver, Duration.ofSeconds(10))
        wait.until(titleIs("Gaia - Dashboard"))
    }

}
