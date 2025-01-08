package cl.testinium.step_definitions;

import cl.testinium.base.Pages;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

public class CommonStepDefinitions {
	@Given("I opened up the Money Transfer page")
	public void iOpenedUpTheMoneyTransferPage() {
		Pages.HOMEPAGE.openMoneyTransferPage();
	}

	@When("I click on the Transfer Money button")
	public void iClickOnTheButton() {
		Pages.Popups.MONEY_TRANSFER_POPUP.openPopup();
	}
}
