Feature: Price alarm page

  Background:
    Given Navigated to "https://lp.morele.net/alarmcenowy/"

  Scenario: Price badge in all product cards
    Then All cards have price badge

  Scenario: Old price on product page
    When Pick random product link and old price
    And Navigate to selected product
    Then Old price on alarm page matches brutto price on product page
