package com.cucumber.pages;

import org.openqa.selenium.WebDriver;

public class FirstPage extends BasePage {
    public FirstPage(WebDriver driver) {
        super(driver);
    }

    public FirstPage switchToPreviewFrame(String key) {
        this.switchToFrame(key);
        return this;
    }

    public FirstPage scrollToFrameBottom() {
        this.scrollToBottom();
        return this;
    }

    public FirstPage selectCompanySituation(String key) {
        this.clickElement(key);
        return this;
    }

    public FirstPage getScreenShoot(String name) {
        this.screenShot(name);
        return this;
    }

    public void andClickNextPage(String key) {
        this.clickElement(key);
    }
}
