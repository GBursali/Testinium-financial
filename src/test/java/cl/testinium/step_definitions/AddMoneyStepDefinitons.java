package cl.testinium.step_definitions;

import cl.testinium.base.Pages;
import cl.testinium.data.CardData;
import cl.testinium.data.TransferData;
import cl.testinium.utils.JsonReader;
import com.gbursali.data.DataManager;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AddMoneyStepDefinitons {
    @Given("I click on the Add money")
    public void iClickOnTheAddMoney() {
        Pages.ADD_MONEY_POPUP.openPopup();
    }

    @And("I add money with the following data:")
    public void iAddMoneyWithTheFollowingData(DataTable table) {
        var data = table.asMaps()
                .stream()
                .map(CardData::fromMap)
                .findFirst()
                .orElseThrow(()->new IllegalArgumentException(JsonReader.getExceptionMessage("Cucumber table is not populated")));
        Pages.ADD_MONEY_POPUP.fillForm(data);
    }

    @And("I add money with the following invalid data:")
    public void iAddMoneyWithTheFollowingInvalidData(DataTable table) {
        var data = table.asMaps()
                .stream()
                .map(CardData::fromMap)
                .findFirst()
                .orElseThrow(()->new IllegalArgumentException(JsonReader.getExceptionMessage("Cucumber table is not populated")));
        Pages.ADD_MONEY_POPUP.fillInvalidForm(data);
    }

    @Then("make sure we got the money")
    public void makeSureWeGotTheMoney() {
        TransferData transferData = DataManager.getFirst(CardData.class)
                .orElseThrow(() -> new IllegalStateException(JsonReader.getExceptionMessage("Account information not saved")))
                .toTransferData();
        Pages.MONEY_TRANSFER_PAGE.verifyTransfer(transferData);
        Pages.MONEY_TRANSFER_PAGE.verifyMoney(transferData.amount);
    }

    @When("I write {string} to the {string} field in the Add Money popup")
    public void iWriteToTheField(String value, String fieldName) {
        Pages.ADD_MONEY_POPUP.inputDynamicValue(fieldName,value);
    }

    @Then("i should have {string} error on the {string} field")
    public void iShouldHaveErrorOnTheField(String expectedText, String fieldLabel) {
        Pages.ADD_MONEY_POPUP.verifyError(fieldLabel,expectedText);
    }

    @And("I close the Add Money popup")
    public void iCloseTheAddMoneyPopup() {
        Pages.ADD_MONEY_POPUP.cancel();
    }
}
