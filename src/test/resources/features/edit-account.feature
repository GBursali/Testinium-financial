@edit-account
Feature: Edit account

    Background: I log in to the system
        Given I log in to the system
        And I opened up the Money Transfer page
        And I have an account with a non-zero balance
        * save the current account details
        When I click on the Edit Account button

    @edit_account_name_with_empty_and_full_text
    Scenario: Edit account name with different text options
        Then verify the current account name in the popup is correct
        When I fill the account name with ""
        Then verify the Update button in the edit account popup is disabled
        When I fill the account name with "<random>"
        Then verify the Update button in the edit account popup is enabled
        When I fill the account name with "<huge text>"
        Then verify the Update button in the edit account popup is disabled
        # Note to BA: An error message here maybe?

    @transfer @edit_account_name_and_make_a_transfer_verify_new
    Scenario: User edits the account name and makes a transfer
        Then verify the current account name in the popup is correct
        When I fill the account name with "<random>"
        And I update my new Account Name
        * update the current account details
        When I click on the Transfer Money button
        And I make the following transfer:
            | senderAccount  | receiverAccount | amount |
            | <Account Name> | Testinium-2     | 3000   |
        Then I verify the transaction is completed
