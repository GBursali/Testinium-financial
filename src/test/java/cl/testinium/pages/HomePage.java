package cl.testinium.pages;

import cl.testinium.base.Driver;
import com.gbursali.elements.HTMLElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

	@FindBy(xpath = "//div[./div[text()='Open Money Transfer']]")
	public HTMLElement openMoneyTransfer;

	public HomePage() {
		PageFactory.initElements(HTMLElement.getDecorator(Driver.getDriver()),this);
	}
	public void openMoneyTransferPage() {
		openMoneyTransfer.click();
	}
}
