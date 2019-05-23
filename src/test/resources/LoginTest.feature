Feature: User Login Feature

  @smoke @login
  Scenario: User Login scenario
    Given User is at the login page of the application
    And Enter username "appium@getir.com"
    And Enter password "appium"
    And Click log-in button
    And Is user at the home page?