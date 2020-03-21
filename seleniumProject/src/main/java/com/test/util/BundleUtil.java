package com.test.util;

import org.openqa.selenium.By;
import org.testng.Assert;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class BundleUtil {
    Properties properties;

    public BundleUtil(String filePath) {
        properties = new Properties();
        try {
            properties.load(new BufferedReader(new FileReader(filePath)));
        } catch (IOException e) {
            Assert.fail("Failed to load the properties file.");
            e.printStackTrace();
        }
    }

    public String getString(String key) {
        return properties.getProperty(key);
    }

    /*
     *  Get locator by different methods.
     */
    public By getLocator(String key) {
        String locator = properties.getProperty(key);
        String locatorType = locator.split(">")[0];
        String locatorValue = locator.split(">")[1];

        if (locatorType.toLowerCase().equals("id")) {
            return By.id(locatorValue);
        } else if (locatorType.toLowerCase().equals("name")) {
            return By.name(locatorValue);
        } else if (locatorType.toLowerCase().equals("classname")) {
            return By.className(locatorValue);
        } else if (locatorType.toLowerCase().equals("tagname")) {
            return By.tagName(locatorValue);
        } else if (locatorType.toLowerCase().equals("linktext")) {
            return By.linkText(locatorValue);
        } else if (locatorType.toLowerCase().equals("partiallinktext")) {
            return By.partialLinkText(locatorValue);
        } else if (locatorType.toLowerCase().equals("cssselector")) {
            return By.cssSelector(locatorValue);
        } else if (locatorType.toLowerCase().equals("xpath")) {
            return By.xpath(locatorValue);
        } else {
            Assert.fail("Not supported method.");
            return null;
        }
    }
}
