Feature: To verify no duplicate link present in the footer


  Scenario: Verify number of slides and their duration with expected data
  
  Given User is on the "Bulls" home page
  When User scroll down to footer
  Then User read all the footer links under each section
  Then User check for duplicate links in the footer
  Then User save all the footer links in a CSV file
  