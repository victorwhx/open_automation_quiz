Feature: Fill in the forms on different pages.
         Verify whether commit OK or not.
  Scenario: simple scenario for multiple conditions
    Given open the browser and enter the url: "https://templates.jinshuju.net/detail/Dv9JPD"
    When the first page is displayed
    And select company type, take a screen shot, enter the second page
    When the second page is displayed
    And input date, applier, applier`s contact info, take a screenshot, enter the third page
    When the third page is displayed
    And input company info, workers, date, warning info, person in charge, content info, take a screenshot, enter the result page
    When the result page is displayed
    Then commit result "提交成功" is expected.