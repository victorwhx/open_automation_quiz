package com.cucumber.pages;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class ResultPage extends BasePage {
    public ResultPage(WebDriver driver) {
        super(driver);
    }

    public void verifyResult(String key, String expectedResult) {
        String result = getElementText(key);
        this.screenShot(this.getClass().getName());
        Assert.assertEquals(result, expectedResult);
    }
}
