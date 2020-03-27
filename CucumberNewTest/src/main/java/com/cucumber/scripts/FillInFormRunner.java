package com.cucumber.scripts;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import cucumber.api.testng.TestNGCucumberRunner;

@CucumberOptions(
        features = {"src/main/resources/fillInform.feature"},
        plugin={"pretty", "html:target/cucumber", "json:target/cucumber/cucumber.json", "junit:target/junit"}
)
public class FillInFormRunner extends AbstractTestNGCucumberTests {
    private TestNGCucumberRunner testNGCucumberRunner;
}
