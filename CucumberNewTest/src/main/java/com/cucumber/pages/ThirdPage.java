package com.cucumber.pages;

import org.openqa.selenium.WebDriver;

public class ThirdPage extends BasePage {
    public ThirdPage(WebDriver driver) {
        super(driver);
    }

    public ThirdPage inputCompany(String key, String inputValue) {
        this.sendKeysForInput(key, inputValue);
        return this;
    }

    public ThirdPage inputWorkers(String key, String inputValue) {
        this.sendKeysForInput(key, inputValue);
        return this;
    }

    public ThirdPage inputDate(String key, String inputValue) {
        this.sendKeysForInput(key, inputValue);
        return this;
    }

    public ThirdPage inputWarning(String key, String inputValue) {
        this.sendKeysForInput(key, inputValue);
        return this;
    }

    public ThirdPage inputCEO(String key, String inputValue) {
        this.sendKeysForInput(key, inputValue);
        return this;
    }

    public ThirdPage inputContact(String key, String inputValue) {
        this.sendKeysForInput(key, inputValue);
        return this;
    }

    public ThirdPage inputContent(String key, String inputValue) {
        this.sendKeysForInput(key, inputValue);
        return this;
    }

    public ThirdPage getScreenShoot(String name) {
        this.screenShot(name);
        return this;
    }

    public void andClickCommintButton(String key) {
        this.clickElement(key);
    }
}
