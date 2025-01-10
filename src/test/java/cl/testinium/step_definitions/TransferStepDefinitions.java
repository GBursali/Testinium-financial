package cl.testinium.step_definitions;

import cl.testinium.base.Pages;
import cl.testinium.data.TransferData;
import cl.testinium.utils.JsonReader;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class TransferStepDefinitions {
	@Given("I have an account with a non-zero balance")
	public void iHaveAnAccountWithANonZeroBalance() {
		Pages.MONEY_TRANSFER_PAGE.ensureAccountNonZero();
	}

	@And("I make the following transfer:")
	public void iMakeTheFollowingTransfer(DataTable data) {
		TransferData input =  data.asMaps()
				.stream()
				.map(TransferData::fromMap)
				.findFirst()
				.orElseThrow(()->new IllegalArgumentException(JsonReader.getExceptionMessage("Cucumber table is not populated")));
		Pages.MONEY_TRANSFER_POPUP.fillTheForm(input);
	}

	@Then("I verify the transaction is completed")
	public void iVerifyTheTransactionIsCompleted() {
		Pages.MONEY_TRANSFER_PAGE.verifyTransfer();
	}
}
