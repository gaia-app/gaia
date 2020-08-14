package io.gaia_app.e2e.pages

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy

class JobPage(webDriver: WebDriver) {

    init {
        if(! webDriver.currentUrl.contains("/jobs/")){
            throw IllegalStateException("This is not the job page. Current page is ${webDriver.currentUrl}")
        }
    }

    @FindBy(className="job-detail-title")
    private lateinit var jobDetailTitle: WebElement

    fun jobDetailTitle() : String = jobDetailTitle.text

}
