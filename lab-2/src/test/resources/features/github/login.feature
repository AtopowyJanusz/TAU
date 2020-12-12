Feature: Login page

  Background:
    Given Navigated to "https://github.com/login"

  Scenario: Passing incorrect login data
    When Enter login and password "randomlogin@w" "randompassword"
    And Click login button
    Then Error flash exists with message "Incorrect username or password."

  Scenario: We can navigate to lost password site
    Then "Forgot password?" link exists
    When Navigated to "Forgot password?" link
    Then Header1 "Reset your password" exists

  Scenario: We can navigate to account creation page
    Then "Create an account" link exists
    When Navigated to "Create an account" link
    Then Header1 "Create your account" exists
