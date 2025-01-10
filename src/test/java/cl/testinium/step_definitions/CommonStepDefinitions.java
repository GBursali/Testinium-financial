package cl.testinium.step_definitions;

import cl.testinium.base.Pages;
import cl.testinium.data.CurrentAccountData;
import com.gbursali.data.DataManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

public class CommonStepDefinitions {
    @Given("I opened up the Money Transfer page")
    public void iOpenedUpTheMoneyTransferPage() {
        Pages.HOMEPAGE.openMoneyTransferPage();
    }

    @When("I click on the Transfer Money button")
    public void iClickOnTheButton() {
        Pages.MONEY_TRANSFER_POPUP.openPopup();
    }

    @Given("save/update the current account details")
    public void saveMoney() {
        var data = DataManager.getFirst(CurrentAccountData.class)
                .orElse(new CurrentAccountData());
        data.money = Pages.MONEY_TRANSFER_PAGE.getAccountBalance();
        data.accountName = Pages.MONEY_TRANSFER_PAGE.getAccountName();
        data.save();
    }
}
