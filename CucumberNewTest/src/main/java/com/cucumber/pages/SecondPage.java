package com.cucumber.pages;

import org.openqa.selenium.WebDriver;

public class SecondPage extends BasePage {
    public SecondPage(WebDriver driver) {
        super(driver);
    }

    public SecondPage scrollToFrameBottom() {
        this.scrollToBottom();
        return this;
    }

    public SecondPage inputDate(String key, String inputValue) {
        this.sendKeysForInput(key, inputValue);
        return this;
    }

    public SecondPage inputApplier(String key, String inputValue) {
        this.sendKeysForInput(key, inputValue);
        return this;
    }

    public SecondPage inputContact(String key, String inputValue) {
        this.sendKeysForInput(key, inputValue);
        return this;
    }

    public SecondPage getScreenShoot(String name) {
        this.screenShot(name);
        return this;
    }

    public void andClickNextPage(String key) {
        this.clickElement(key);
    }
}
