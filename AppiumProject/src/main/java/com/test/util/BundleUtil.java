package com.test.util;

import org.openqa.selenium.By;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/*
 * Get value from properties file.
 */
public class BundleUtil {
    Properties properties;

    public BundleUtil(String filePath) {
        properties = new Properties();
        try {
            properties.load(new BufferedReader(new FileReader(filePath)));
        } catch (IOException e) {
            System.out.println("Failed to load the properties file.");
            e.printStackTrace();
        }
    }

    public String getString(String key) {
        return properties.getProperty(key);
    }

    public void replaceProperties(String key, String value) {
        properties.replace(key, value);
    }

    public void clearProperties() {
        properties.clear();
    }

    /*
     *  Get locator by different methods.
     */
    public By getLocator(String key) throws Exception {
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
            throw new Exception("The locator type has not been defined：" + locatorType);
        }
    }
}
