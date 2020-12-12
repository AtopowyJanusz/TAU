# Scenariusze testowe
Znajdują się w: src/test/resources/features

```
Github
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
```

```
Morele
Feature: Price alarm page

  Background:
    Given Navigated to "https://lp.morele.net/alarmcenowy/"

  Scenario: Price badge in all product cards
    Then All cards have price badge

  Scenario: Old price on product page
    When Pick random product link and old price
    And Navigate to selected product
    Then Old price on alarm page matches brutto price on product page
```

# Logi
Przykładowy plik w selenium.log

# Wymagania
- Java 11
- Zainstalowany maven

# Uruchomienie
`mvn test` - wywołanie będzie szukało domyślnego drivera jakim jest chromium. Można także uruchomić z parametrem browser
podając firefox lub opera.
Np. `mvn test -D browser=firefox`
