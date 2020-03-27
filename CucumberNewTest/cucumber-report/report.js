$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("src/main/resources/fillInform.feature");
formatter.feature({
  "line": 1,
  "name": "Fill in the forms on different pages.",
  "description": "       Verify whether commit OK or not.",
  "id": "fill-in-the-forms-on-different-pages.",
  "keyword": "Feature"
});
formatter.scenario({
  "line": 3,
  "name": "simple scenario for multiple conditions",
  "description": "",
  "id": "fill-in-the-forms-on-different-pages.;simple-scenario-for-multiple-conditions",
  "type": "scenario",
  "keyword": "Scenario"
});
formatter.step({
  "line": 4,
  "name": "open the browser and enter the url: \"https://templates.jinshuju.net/detail/Dv9JPD\"",
  "keyword": "Given "
});
formatter.step({
  "line": 5,
  "name": "the first page is displayed",
  "keyword": "When "
});
formatter.step({
  "line": 6,
  "name": "select company type, take a screen shot, enter the second page",
  "keyword": "And "
});
formatter.step({
  "line": 7,
  "name": "the second page is displayed",
  "keyword": "When "
});
formatter.step({
  "line": 8,
  "name": "input date, applier, applier`s contact info, take a screenshot, enter the third page",
  "keyword": "And "
});
formatter.step({
  "line": 9,
  "name": "the third page is displayed",
  "keyword": "When "
});
formatter.step({
  "line": 10,
  "name": "input company info, workers, date, warning info, person in charge, content info, take a screenshot, enter the result page",
  "keyword": "And "
});
formatter.step({
  "line": 11,
  "name": "the result page is displayed",
  "keyword": "When "
});
formatter.step({
  "line": 12,
  "name": "commit result \"提交成功\" is expected.",
  "keyword": "Then "
});
formatter.match({
  "arguments": [
    {
      "val": "https://templates.jinshuju.net/detail/Dv9JPD",
      "offset": 37
    }
  ],
  "location": "FillInFormStepdefs.openTheBrowserAndEnterTheUrl(String)"
});
formatter.result({
  "duration": 25319214700,
  "status": "passed"
});
formatter.match({
  "location": "FillInFormStepdefs.theFirstPageIsDisplayed()"
});
formatter.result({
  "duration": 17429400,
  "status": "passed"
});
formatter.match({
  "location": "FillInFormStepdefs.selectCompanyTypeTakeAScreenShotEnterTheSecondPage()"
});
formatter.result({
  "duration": 1044169100,
  "status": "passed"
});
formatter.match({
  "location": "FillInFormStepdefs.theSecondPageIsDisplayed()"
});
formatter.result({
  "duration": 750700,
  "status": "passed"
});
formatter.match({
  "location": "FillInFormStepdefs.inputDateApplierApplierSContactInfoTakeAScreenshotEnterTheThirdPage()"
});
formatter.result({
  "duration": 1623354300,
  "status": "passed"
});
formatter.match({
  "location": "FillInFormStepdefs.theThirdPageIsDisplayed()"
});
formatter.result({
  "duration": 1789500,
  "status": "passed"
});
formatter.match({
  "location": "FillInFormStepdefs.inputCompanyInfoWorkersDateWarningInfoPersonInChargeContentInfoTakeAScreenshotEnterTheResultPage()"
});
formatter.result({
  "duration": 2019090400,
  "status": "passed"
});
formatter.match({
  "location": "FillInFormStepdefs.theResultPageIsDisplayed()"
});
formatter.result({
  "duration": 741600,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "提交成功",
      "offset": 15
    }
  ],
  "location": "FillInFormStepdefs.commitResultIsExpected(String)"
});
formatter.result({
  "duration": 2039972900,
  "status": "passed"
});
});