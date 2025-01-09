@add_money
Feature: Add money to the account

  Background: Open up the application
    Given I log in to the system
    And I opened up the Money Transfer page

  @positive @add_money_to_the_account
  Scenario: Add positive values to the account
    * save the current account details
    Given I click on the Add money
    And I add money with the following data:
      | Card Number      | Card Holder | Card Expiry Date | Card CVV | Amount |
      | 1234123412341234 | Test UI     | 01/30            | 1234     | 120    |
    Then make sure we got the money

  @negative @add_money_with_invalid_card_number
  Scenario: Add money to the account with invalid values
    * save the current account details
    Given I click on the Add money
    #Something went wrong lol
    When I write "1234123412341234123123" to the "Card number" field in the Add Money popup
    Then i should have "Too Long!" error on the "Card number" field
    When I write "sho" to the "Card holder" field in the Add Money popup
    Then i should have "Too Short!" error on the "Card holder" field
    #Past date?
#    When I write "01/24" to the "Expiry date" field in the Add Money popup
#    Then i should have "Wrong date. Please give a correct date" error on the "Expiry date" field
    When I write "0" to the "CVV" field in the Add Money popup
    Then i should have "Too short" error on the "CVV" field
    And I close the Add Money popup