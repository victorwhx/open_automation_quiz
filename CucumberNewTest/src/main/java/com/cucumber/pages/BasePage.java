package com.cucumber.pages;

import com.cucumber.util.BundleUtil;
import com.google.common.io.Files;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class BasePage {
    private WebDriver driver;
    private WebDriverWait wait;
    private BundleUtil bundle;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 25);
        this.bundle = new BundleUtil("src/main/resources/application.properties");
    }

    void scrollToBottom() {
        ((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    void switchToFrame(String key) {
        WebElement frame = getElement(key);
        driver.switchTo().frame(frame);
    }

    void clickElement(String key) {
        Objects.requireNonNull(getElement(key)).click();
    }

    void sendKeysForInput(String key, String inputValue) {
        Objects.requireNonNull(getElement(key)).sendKeys(inputValue);
    }

    String getElementText(String key) {
        return Objects.requireNonNull(getElement(key)).getText();
    }

    private WebElement getElement(String key) {
        try {
            By locator = bundle.getLocator(key);
            waitForElemnet(key, locator);
            return driver.findElement(locator);
        } catch (Exception e) {
            System.out.println("error: getElement, " + key);
            e.printStackTrace();
            return null;
        }
    }

    private void waitForElemnet(String key, By locator) {
        if (key.contains("Clickable")) {
            wait.until(ExpectedConditions.elementToBeClickable(locator));
        } else {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        }
    }

    void screenShot(String name) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String curTime = sdf.format(new Date());
        String curPath = System.getProperty("user.dir");
        File srcFile = ((RemoteWebDriver) driver).getScreenshotAs(OutputType.FILE);
        try {
            Files.copy(srcFile, new File(curPath + "\\screenshot\\" + name + "_" + curTime + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
