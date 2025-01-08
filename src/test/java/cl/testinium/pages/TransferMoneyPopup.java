package cl.testinium.pages;

import cl.testinium.base.Pages;
import cl.testinium.data.TransferMoneyData;
import cl.testinium.utils.elements.Select;
import com.gbursali.elements.HTMLElement;
import com.gbursali.elements.Textbox;
import com.gbursali.forms.IPopupForm;
import org.openqa.selenium.support.FindBy;

public class TransferMoneyPopup implements IPopupForm<TransferMoneyPopup> {


	@FindBy(xpath = "//div[text()='Sender account']/following-sibling::div[2]/select")
	public Select senderAccount;

	@FindBy(xpath = "//div[text()='Receiver account']/following-sibling::div[2]/select")
	public Select receiverAccount;

	@FindBy(xpath = "//div[text()='Amount']/following-sibling::div[2]/input")
	public Textbox amount;

	@FindBy(xpath = "//div[./div[text()='Send']]")
	public HTMLElement sendButton;

	@FindBy(xpath = "//div[text()='Transfer money']/following-sibling::div")
	public HTMLElement cancelButton;

	public void fillTheForm(TransferMoneyData transferMoneyData) {
		this.senderAccount.select(transferMoneyData.senderAccount);
		this.receiverAccount.select(transferMoneyData.receiverAccount);
		this.amount.sendKeys(String.valueOf(transferMoneyData.amount));
		this.sendButton.click();
	}

	@Override
	public TransferMoneyPopup openPopup() {
		Pages.MONEY_TRANSFER_PAGE.transferMoneyButton.click();
		return new TransferMoneyPopup();
	}

	@Override
	public void save() {
		sendButton.click();
	}

	@Override
	public void cancel() {
		cancelButton.click();
	}
}
