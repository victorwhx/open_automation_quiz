Feature: Fill in and submit the form.
  Fill in all the required content on the different pages and submit the form.

  Scenario Outline: Fill in and submit the form.
    # driver only init once before the firstPage is displayed
    Given enter the website: "https://templates.jinshuju.net/detail/Dv9JPD"
    # fill in the required content on different pages
    When fill in the required content on "<testPage>" and visit the next page.
    # verify the result after the resultPage is displayed.
    Then verify the result, "提交成功" is expected.

    Examples:
      | testPage    |
      | firstPage   |
      | secondPage  |
      | thirdPage   |
      | resultPage  |
