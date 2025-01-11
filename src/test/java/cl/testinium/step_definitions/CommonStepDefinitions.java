package cl.testinium.step_definitions;

import cl.testinium.base.Pages;
import cl.testinium.data.CurrentAccountData;
import com.gbursali.data.DataManager;
import com.gbursali.elements.HTMLElement;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.awaitility.Awaitility;
import org.junit.Assert;
import org.openqa.selenium.By;

import java.time.Duration;
import java.util.Optional;
import java.util.function.Function;

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

    @And("I should see the error message {string}")
    public void iShouldSeeTheErrorMessage(String error) {
        final Function<String, Optional<HTMLElement>> errorMessageFinder =
                x -> HTMLElement.findElement(By.xpath("//div[./div[text()='%s']]".formatted(x)));

        Awaitility.await("Waiting for error message")
                .pollInterval(Duration.ofMillis(100))
                .until(() -> errorMessageFinder.apply(error).isPresent());
    }
}
