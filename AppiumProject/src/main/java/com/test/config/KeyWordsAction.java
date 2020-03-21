package com.test.config;

import com.test.script.TestApp;
import com.test.util.BundleUtil;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class KeyWordsAction {
    private static BundleUtil bundle;
    static AndroidDriver<AndroidElement> driver;
    private static WebDriverWait wait;

    /*
     * Init and start Wandoujia App
     */
    public static void startTest() {
        bundle = new BundleUtil(ConstantParameters.propertiesPath);
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName",bundle.getString("capabilityValue.deviceName"));
        capabilities.setCapability("platformVersion",bundle.getString("capabilityValue.platformVersion"));
        capabilities.setCapability("platformName",bundle.getString("capabilityValue.platformName"));
        capabilities.setCapability("appPackage", bundle.getString("capabilityValue.appPackage"));
        capabilities.setCapability("appActivity", bundle.getString("capabilityValue.appActivity"));
        capabilities.setCapability("noSign",
                                    Boolean.valueOf(bundle.getString("capabilityValue.noSign")).booleanValue());

        try {
            driver = new AndroidDriver<>(new URL(bundle.getString("url") + "/wd/hub"), capabilities);
            wait = new WebDriverWait(driver, 15);
        } catch (MalformedURLException e) {
            TestApp.setTestResult(false);
            e.printStackTrace();
        }
    }

    /*
     * Click the element.
     */
    public static void click(String locator) {
        try {
            Objects.requireNonNull(getElement(locator)).click();
        } catch (Exception e) {
            TestApp.setTestResult(false);
            e.printStackTrace();
        }
    }

    /*
     * Input the keywords to search
     */
    public static void input(String locator, String data) {
        try {
            AndroidElement input = getElement(locator);
            Objects.requireNonNull(input).clear();
            input.sendKeys(data);
        } catch (Exception e) {
            TestApp.setTestResult(false);
            e.printStackTrace();
        }
    }

    /*
     * Select the target App
     */
    public static void selectTargetApp(String locator, String data) {
        try {
            String tmpValue = bundle.getString(locator) + data + "\")";
            bundle.replaceProperties(locator, tmpValue);
             Objects.requireNonNull(getElement(locator)).click();
        } catch (Exception e) {
            TestApp.setTestResult(false);
            e.printStackTrace();
        }
    }

    /*
     * Verify App name
     */
    public static void verifyTargetApp(String locator, String data) {
        try {
            String tmpValue = bundle.getString(locator) + data + "\")";
            bundle.replaceProperties(locator, tmpValue);
            String appName = Objects.requireNonNull(getElement(locator)).getText();
            Assert.assertEquals(appName, data);
        } catch (Exception e) {
            TestApp.setTestResult(false);
            e.printStackTrace();
        }
    }

    /*
     * End test.
     */
    public static void endTest() {
        bundle.clearProperties();
        driver.quit();
    }

    /*
     * Get an element by findElement()
     * or findElementByAndroidUIAutomator()
     */
    private static AndroidElement getElement(String key) {
        try {
            By locator;
            if (key.contains("UIAutomator")) {
                locator = bundle.getLocator(key.substring(0, key.indexOf("_")));
                wait.until(waitCondition(key, locator));
                return driver.findElementByAndroidUIAutomator(bundle.getString(key));
            }
            locator = bundle.getLocator(key);
            wait.until(waitCondition(key, locator));
            return driver.findElement(locator);
        } catch (Exception e) {
            TestApp.setTestResult(false);
            e.printStackTrace();
            return null;
        }
    }

    private static ExpectedCondition<WebElement> waitCondition(String key, By locator) {
        if (key.contains("Btn")) {
            return ExpectedConditions.elementToBeClickable(locator);
        } else {
            return ExpectedConditions.presenceOfElementLocated(locator);
        }
    }
}
