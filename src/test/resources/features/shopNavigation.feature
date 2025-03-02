Feature: Navigate to men jacket section and extract details from CP1


 @AttachReport
  Scenario: Verify jacket title, price under men section and create report
  
    Given User is on the "Warriors" home page
    When User hovers over Shop and clicks on Men's
    Then User selects jackets radio button
    Then User extracts and saves jacket details and save in the report
