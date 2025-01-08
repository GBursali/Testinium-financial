@transfer
Feature: Transfer money to another account

  Background: I have money in mny account
    Given I opened up the Money Transfer page
    And I have an account with a non-zero balance

  @positive @send-money
  Scenario: User sends money to another account
    When I click on the Transfer Money button
    And I make the following transfer:
    | senderAccount | receiverAccount | amount |
    |Initial Acc    |Testinium-1      |3000    |
