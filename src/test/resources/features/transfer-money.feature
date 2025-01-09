@transfer
Feature: Transfer money to another account

  Background: I have money in mny account
    Given I log in to the system
    And I opened up the Money Transfer page
    And I have an account with a non-zero balance

  @positive @send_money @transfer_money_to_another_account
  Scenario: User sends money to another account
    When I click on the Transfer Money button
    * save the current money
    And I make the following transfer:
      | senderAccount | receiverAccount | amount |
      | Initial acc   | Testinium-2     | 3000   |
    Then I verify the transaction is completed

  @negative @send_money @transfer_money_to_another_account
  Scenario: User sends money to another account
    When I click on the Transfer Money button
    * save the current money
    And I make the following transfer:
      | senderAccount | receiverAccount | amount |
      | Initial acc   | Testinium-2     | -110   |
    Then I verify the transaction is completed
