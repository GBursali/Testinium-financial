package cl.testinium.step_definitions;

import cl.testinium.base.Pages;
import cl.testinium.data.TransferMoneyData;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import org.junit.Assert;

import java.util.List;

public class TransferStepDefinitions {
	@Given("I have an account with a non-zero balance")
	public void iHaveAnAccountWithANonZeroBalance() {
		Pages.MONEY_TRANSFER_PAGE.ensureAccountNonZero();
	}

	@And("I make the following transfer:")
	public void iMakeTheFollowingTransfer(DataTable data) {
		List<TransferMoneyData> input = data.asList(TransferMoneyData.class);
		Assert.assertEquals(1, input.size());
		Pages.Popups.MONEY_TRANSFER_POPUP.fillTheForm(input.get(0));
	}
}
