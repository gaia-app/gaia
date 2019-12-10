package io.codeka.gaia.e2e

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory

class LoginPage(val webDriver: WebDriver) {

    init {
        if(! webDriver.currentUrl.contains("login")){
            throw IllegalStateException("This is not the login page. Current page is ${webDriver.currentUrl}")
        }
    }

    @FindBy(name="username")
    lateinit var loginInput: WebElement

    @FindBy(name="password")
    lateinit var passwordInput: WebElement

    @FindBy(tagName = "button")
    lateinit var submitButton: WebElement

    fun login(login: String, password: String){
        if(webDriver.currentUrl.contains("login")){
            loginInput.sendKeys(login)
            passwordInput.sendKeys(password)
            submitButton.click()
        }
        return PageFactory.initElements(this.webDriver, DashboardPage::class)
    }

}