package com.cucumber.scripts;

import com.cucumber.pages.FirstPage;
import com.cucumber.pages.ResultPage;
import com.cucumber.pages.SecondPage;
import com.cucumber.pages.ThirdPage;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class FillInFormStepdefs {
    private WebDriver driver;
    private FirstPage firstPage;
    private SecondPage secondPage;
    private ThirdPage thirdPage;
    private ResultPage resultPage;
    private String curDate;

    @Given("^open the browser and enter the url: \"([^\"]*)\"$")
    public void openTheBrowserAndEnterTheUrl(String url) {
        this.driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
        try {
            driver.get(url);
        } catch (WebDriverException e) {
            System.out.println("Page did not load completely within 15s.");
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        curDate = formatter.format(date);
    }

    @When("^the first page is displayed$")
    public void theFirstPageIsDisplayed() {
        firstPage = new FirstPage(this.driver);
    }

    @And("^select company type, take a screen shot, enter the second page$")
    public void selectCompanyTypeTakeAScreenShotEnterTheSecondPage() {
        firstPage.switchToPreviewFrame("firstPage.iframe")
                .scrollToFrameBottom()
                .selectCompanySituation("firstPage.radioClickable")
                .getScreenShoot(FirstPage.class.getName())
                .andClickNextPage("firstPage.nextPageClickable");
    }

    @When("^the second page is displayed$")
    public void theSecondPageIsDisplayed() {
        secondPage = new SecondPage(this.driver);
    }

    @And("^input date, applier, applier`s contact info, take a screenshot, enter the third page$")
    public void inputDateApplierApplierSContactInfoTakeAScreenshotEnterTheThirdPage() {
        secondPage.scrollToFrameBottom()
                .inputDate("secondPage.dateInput", curDate)
                .inputApplier("secondPage.applierInput", "自动化")
                .inputContact("secondPage.contactInput", "13888888888")
                .getScreenShoot(SecondPage.class.getName())
                .andClickNextPage("secondPage.nextPageClickable");
    }

    @When("^the third page is displayed$")
    public void theThirdPageIsDisplayed() {
        thirdPage = new ThirdPage(driver);
    }

    @And("^input company info, workers, date, warning info, person in charge, content info, take a screenshot, enter the result page$")
    public void inputCompanyInfoWorkersDateWarningInfoPersonInChargeContentInfoTakeAScreenshotEnterTheResultPage() {
        thirdPage.inputCompany("thirdPage.companyInput", "测试公司")
                .inputWorkers("thirdPage.workersInput", "99")
                .inputDate("thirdPage.dateInput", curDate)
                .inputWarning("thirdPage.warningInput", "0")
                .inputCEO("thirdPage.ceoInput", "CEO")
                .inputContact("thirdPage.contactInput", "13888888888")
                .inputContent("thirdPage.contentInput", "测试内容")
                .getScreenShoot(thirdPage.getClass().getName())
                .andClickCommintButton("thirdPage.commitBtn");
    }

    @When("^the result page is displayed$")
    public void theResultPageIsDisplayed() {
        resultPage = new ResultPage(driver);
    }

    @Then("^commit result \"([^\"]*)\" is expected\\.$")
    public void commitResultIsExpected(String expectedResult) {
        resultPage.verifyResult("resultPage.expectedResult", expectedResult);
        driver.quit();
    }
}
