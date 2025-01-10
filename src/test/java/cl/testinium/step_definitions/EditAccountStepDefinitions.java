package cl.testinium.step_definitions;

import cl.testinium.base.Pages;
import cl.testinium.utils.TextUtils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class EditAccountStepDefinitions {
    @When("I click on the Edit Account button")
    public void iClickOnTheEditAccountButton() {
        Pages.EDIT_ACCOUNT_POPUP.openPopup();
    }

    @Then("verify the current account name in the popup is correct")
    public void verifyTheCurrentAccountNameIsCorrect() {

        Pages.EDIT_ACCOUNT_POPUP.verifyAccountName();
    }

    @When("I fill the account name with {string}")
    public void iFillTheAccountNameWith(String newAccountName) {
        Pages.EDIT_ACCOUNT_POPUP.fillAccountName(TextUtils.commonPlaceholders.apply(newAccountName));
    }

    @Then("verify the Update button in the edit account popup is disabled")
    public void verifyTheUpdateButtonInTheEditAccountPopupIsDisabled() {
        Pages.EDIT_ACCOUNT_POPUP.verifyButtonDisabled(true);
    }

    @Then("verify the Update button in the edit account popup is enabled")
    public void verifyTheUpdateButtonInTheEditAccountPopupIsEnabled() {
        Pages.EDIT_ACCOUNT_POPUP.verifyButtonDisabled(false);
    }

    @And("I update my new Account Name")
    public void iUpdateMyNewAccountName() {
        Pages.EDIT_ACCOUNT_POPUP.save();
    }
}
