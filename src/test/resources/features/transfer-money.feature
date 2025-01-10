@transfer
Feature: Transfer money to another account

  Background: I have money in mny account
    Given I log in to the system
    And I opened up the Money Transfer page
    And I have an account with a non-zero balance
    * save the current account details
    When I click on the Transfer Money button

  @positive @send_money @transfer_money_to_another_account
  Scenario: User sends money to another account
    And I make the following transfer:
      | senderAccount  | receiverAccount | amount |
      | <Account Name> | Testinium-2     | 3000   |
    Then I verify the transaction is completed

  @send_money @try_big_amounts
  Scenario: User sends money to another account
    And I make the following transfer:
      | senderAccount  | receiverAccount | amount      |
      | <Account Name> | Testinium-2     | 99999999999 |
    Then I verify the transaction is completed
