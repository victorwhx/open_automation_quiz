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
    private static WebDriver driver;
    private static String curDate;
    private ResultPage resultPage;

    @Given("^enter the website: \"([^\"]*)\"$")
    public void enterTheWebsite(String url) {
        if (driver == null) {
            driver = new ChromeDriver();
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
    }

    @When("^fill in the required content on \"([^\"]*)\" and visit the next page\\.$")
    public void fillInRequiredContentAndVisitNextPage(String testPage) {
        switch (testPage) {
            case "firstPage":
                new FirstPage(driver).switchToPreviewFrame("firstPage.iframe")
                        .scrollToFrameBottom()
                        .selectCompanySituation("firstPage.radioClickable")
                        .getScreenShoot(testPage)
                        .andClickNextPage("firstPage.nextPageClickable");
                break;
            case "secondPage":
                new SecondPage(driver).scrollToFrameBottom()
                        .inputDate("secondPage.dateInput", curDate)
                        .inputApplier("secondPage.applierInput", "自动化")
                        .inputContact("secondPage.contactInput", "13888888888")
                        .getScreenShoot(testPage)
                        .andClickNextPage("secondPage.nextPageClickable");
                break;
            case "thirdPage":
                new ThirdPage(driver).inputCompany("thirdPage.companyInput", "测试公司")
                        .inputWorkers("thirdPage.workersInput", "99")
                        .inputDate("thirdPage.dateInput", curDate)
                        .inputWarning("thirdPage.warningInput", "0")
                        .inputCEO("thirdPage.ceoInput", "CEO")
                        .inputContact("thirdPage.contactInput", "13888888888")
                        .inputContent("thirdPage.contentInput", "测试内容")
                        .getScreenShoot(testPage)
                        .andClickCommitButton("thirdPage.commitBtn");
                break;
            case "resultPage":
                resultPage = new ResultPage(driver);
                break;
            default:
                break;
        }
    }

    @Then("^verify the result, \"([^\"]*)\" is expected\\.$")
    public void verifyTheResultIsExpected(String expectedResult){
        if (resultPage != null) {
            resultPage.verifyResult("resultPage.expectedResult", expectedResult);
            driver.quit();
        }
    }
}
