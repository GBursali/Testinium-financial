@login
Feature: User can login and logout to the system

  @positive @verify_landing_data
  Scenario: User can login to the system
    Given I log in to the system
    Then verify that I am on the home page
    And the page should have the following data:
      | Label           | Value               |
      | Challenge name: | QE Uygulama         |
      | Start time:     | 08-01-2025 10:00:02 |
      | End time:       | 12-01-2025 23:59:39 |
      | Manager name:   | QAManager           |
      | Company name:   | Testinium           |
