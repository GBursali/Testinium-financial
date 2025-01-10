package cl.testinium.pages;

import com.gbursali.elements.HTMLElement;
import org.openqa.selenium.support.FindBy;

public class HomePage {

	@FindBy(xpath = "//div[./div[text()='Open Money Transfer']]")
	public HTMLElement openMoneyTransfer;

	public HomePage() {
//		PageFactory.initElements(HTMLElement.getDecorator(Driver.getDriver()),this);
	}
	public void openMoneyTransferPage() {
		openMoneyTransfer.waitFor.clickability().click();
	}
}
