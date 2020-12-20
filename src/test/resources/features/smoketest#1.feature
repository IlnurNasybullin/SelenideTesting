@smokeTest
Feature: smoke test #1, check my site

  @success
  Scenario: authorize in site
    Given open email authorize page
    When type to input with placeholder "email" text:"alice@yandex.ru"
    And type to input with placeholder "password" text:"AlicePassword"
    And press input submit button with value "Login"
    And wait until form frame disappears
    Then element with name "Личный кабинет" should exist

  @success
  Scenario: add in database new record
    Given open email authorize page
    When go to link "Sign up"
    And type to input with placeholder "username" text:"Frank"
    And type to input with placeholder "email" text:"frank@microsoft.ru"
    And type to input with placeholder "password" text:"FrankPassword"
    And press input submit button with value "Sign up"
    And wait until form frame disappears
    And press button with text "Выход"
    And type to input with placeholder "email" text:"frank@microsoft.ru"
    And type to input with placeholder "password" text:"FrankPassword"
    And press input submit button with value "Login"
    And wait until form frame disappears
    Then element with name "Личный кабинет" should exist

  @success
  Scenario: search in site
    Given open email authorize page
    When type to input with placeholder "email" text:"bob@gmail.com"
    And type to input with placeholder "password" text:"BobPassword"
    And press input submit button with value "Login"
    And wait until form frame disappears
    And press button with text "Личный кабинет"
    And wait until form frame appears
    Then element with name "name" has value "Bob"

  @success
  Scenario: exit from account
    Given open email authorize page
    When type to input with placeholder "email" text:"bob@gmail.com"
    And type to input with placeholder "password" text:"BobPassword"
    And press input submit button with value "Login"
    And wait until form frame disappears
    And press button with text "Выход"
    And wait until form frame appears
    Then page is authorize page