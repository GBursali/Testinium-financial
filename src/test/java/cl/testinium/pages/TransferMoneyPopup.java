package cl.testinium.pages;

import cl.testinium.base.Driver;
import cl.testinium.base.Pages;
import cl.testinium.data.TransferData;
import cl.testinium.utils.TextUtils;
import cl.testinium.utils.elements.Select;
import com.gbursali.elements.HTMLElement;
import com.gbursali.elements.Textbox;
import com.gbursali.forms.IPopupForm;
import org.awaitility.Awaitility;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;
import java.time.LocalDateTime;

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

	public TransferMoneyPopup() {
		PageFactory.initElements(HTMLElement.getDecorator(Driver.getDriver()), this);
	}

	public void fillTheForm(TransferData transferData) {
		this.senderAccount.select(transferData.senderAccount);
		this.receiverAccount.select(transferData.receiverAccount);
		this.amount.sendKeys(TextUtils.formatToText(transferData.amount));
		this.sendButton.click();
		Awaitility.await().pollDelay(Duration.ofSeconds(1)).until(()->true);
		transferData.sendingDate = LocalDateTime.now();
		transferData.save();
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
