package cl.testinium.pages;

import cl.testinium.api.APIRequests;
import cl.testinium.base.Driver;
import cl.testinium.utils.JsonReader;
import com.gbursali.elements.HTMLElement;
import com.machinezoo.noexception.Exceptions;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.text.NumberFormat;

public class MoneyTransferPage {

	@FindBy(xpath = "//div[./div[text()='Amount']]//div[./following-sibling::*[name()='svg']]")
	public HTMLElement accountBalance;

	@FindBy(xpath = "//div[./div[text()='Transfer money']]")
	public HTMLElement transferMoneyButton;


	public double getAccountBalance() {
		NumberFormat formatter = NumberFormat.getInstance();
		return Exceptions
				.wrap(e -> new IllegalStateException(JsonReader.getExceptionMessage("Balance is not a valid number"), e))
				.get(() -> formatter.parse(accountBalance.getText()).doubleValue());
	}

	public MoneyTransferPage() {
		PageFactory.initElements(HTMLElement.getDecorator(Driver.getDriver()), this);
	}

	public void ensureAccountNonZero() {
		if (getAccountBalance() == 0) {
			LogManager.getLogger().info("Account balance is 0. Adding money from API to continue the flow.");
			APIRequests.topup();
			LogManager.getLogger().info("New balance: " + getAccountBalance());
		}
	}
}
