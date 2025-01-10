package cl.testinium.pages;

import cl.testinium.base.Driver;
import cl.testinium.data.CurrentAccountData;
import cl.testinium.utils.JsonReader;
import com.gbursali.data.DataManager;
import com.gbursali.elements.HTMLElement;
import com.gbursali.elements.Textbox;
import com.gbursali.forms.IPopupForm;
import org.awaitility.Awaitility;
import org.junit.Assert;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;

public class EditAccountPopup implements IPopupForm<EditAccountPopup> {

    @FindBy(xpath = "//div[./div[text()='Edit account']]")
    public HTMLElement editAccountButton;
    @FindBy(xpath = "//div[./div[text()='UPDATE']]")
    public HTMLElement updateButton;

    @FindBy(xpath = "//div[text()='Edit account']/following-sibling::div")
    public HTMLElement cancelButton;
    @FindBy(xpath = "//div[text()='Account name']/following-sibling::div[2]/input")
    public Textbox accountName;

    public void fillAccountName(String accountName) {
        this.accountName.clear().sendKeys(accountName);
    }
    public EditAccountPopup() {
        PageFactory.initElements(HTMLElement.getDecorator(Driver.getDriver()),this);
    }
    @Override
    public EditAccountPopup openPopup() {
        editAccountButton.waitFor.clickability().click();
        return this;
    }

    @Override
    public void save() {
        updateButton.waitFor.clickability().click();
        Awaitility.await().pollDelay(Duration.ofSeconds(1)).until(()->true);

    }

    @Override
    public void cancel() {
        cancelButton.click();
    }

    public void verifyAccountName() {
        var expectedAccountName = DataManager.getFirst(CurrentAccountData.class)
                .map(x -> x.accountName)
                .orElseThrow(()->new IllegalStateException(JsonReader.getExceptionMessage("Account information not saved")));
        var actualAccountName=accountName.getAttribute("value")
                        .orElseThrow(()->new IllegalStateException(JsonReader.getExceptionMessage("Account information not saved")));
        Assert.assertEquals(
                JsonReader.getExceptionMessage("Doesn't have attribute","value"),
                expectedAccountName,
                actualAccountName);
    }

    public void verifyButtonDisabled(boolean expected) {
        var isButtonDisabled = updateButton.getAttribute("aria-disabled")
                .map("true"::equals)
                .orElse(false);
        Assert.assertEquals(
                JsonReader.getExceptionMessage("Button should be disabled","Update"),
                expected,
                isButtonDisabled);
    }
}
