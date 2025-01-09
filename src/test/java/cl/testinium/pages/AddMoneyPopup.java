package cl.testinium.pages;

import cl.testinium.base.Driver;
import cl.testinium.data.CardData;
import cl.testinium.utils.JsonReader;
import cl.testinium.utils.PopupUtils;
import com.gbursali.elements.HTMLElement;
import com.gbursali.elements.Textbox;
import com.gbursali.forms.IPopupForm;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.Function;

public class AddMoneyPopup implements IPopupForm<AddMoneyPopup> {

    @FindBy(xpath = "//div[./div[text()='Add money']]")
    public HTMLElement addMoneyButton;
    @FindBy(xpath = "//div[text()='Card number']/following-sibling::input")
    public Textbox cardNumber;
    @FindBy(xpath = "//div[text()='Card number']/following-sibling::div")
    public HTMLElement cardNumberError;
    @FindBy(xpath = "//div[text()='Card holder']/following-sibling::input")
    public Textbox cardHolder;
    @FindBy(xpath = "//div[text()='Expiry date']/following-sibling::input")
    public Textbox expiryDate;
    @FindBy(xpath = "//div[text()='CVV']/following-sibling::input")
    public Textbox cvv;
    @FindBy(xpath = "//div[text()='Amount']/following-sibling::input")
    public Textbox amount;
    @FindBy(xpath = "//div[./div[text()='Add']]")
    public HTMLElement addButton;
    @FindBy(xpath = "//div[text()='Add money']/following-sibling::div")
    public HTMLElement cancelButton;

    public AddMoneyPopup() {
        PageFactory.initElements(HTMLElement.getDecorator(Driver.getDriver()), this);
    }

    public void fillForm(CardData data) {
        cardNumber.waitFor.existence().sendKeys(data.cardNumber);
        cardHolder.waitFor.existence().sendKeys(data.cardHolder);
        expiryDate.waitFor.existence().sendKeys(data.cardExpiry);
        cvv.waitFor.existence().sendKeys(data.cvv);
        amount.waitFor.existence().sendKeys(String.valueOf(data.amount));
        addButton.click();
        data.date = LocalDateTime.now();
        data.save();
        PopupUtils.waitForClosure();
    }

    @Override
    public AddMoneyPopup openPopup() {
        addMoneyButton.waitFor.clickability().click();
        return this;
    }

    @Override
    public void save() {
        addButton.waitFor.clickability().click();
    }

    @Override
    public void cancel() {
        cancelButton.waitFor.clickability().click();
    }

    public void fillInvalidForm(CardData data) {
        cardNumber.waitFor.existence().sendKeys(data.cardNumber);
        Assert.assertFalse(data.checkCardNumberRule());
        cardNumberError.verify.existence();
        cardHolder.waitFor.existence().sendKeys();
        expiryDate.waitFor.existence().sendKeys(data.cardExpiry);
        cvv.waitFor.existence().sendKeys(data.cvv);
        amount.waitFor.existence().sendKeys(String.valueOf(data.amount));
    }

    public void inputDynamicValue(String fieldName, String value) {
        final Function<String, Optional<HTMLElement>> labelFinder =
                x -> HTMLElement.findElement(By.xpath("//div[text()='%s']".formatted(x)));
        final Function<String, Optional<HTMLElement>> inputFinder =
                x -> HTMLElement.findElement(By.xpath("//div[text()='%s']/following-sibling::input".formatted(x)));

        inputFinder.apply(fieldName)
                .orElseThrow(()->new IllegalStateException(JsonReader.getExceptionMessage("Element not found")))
                .waitFor.clickability()
                .sendKeys(value);

        labelFinder.apply(fieldName)
                .get().click();
    }

    public void verifyError(String fieldLabel, String expectedText) {
        final Function<String, Optional<HTMLElement>> errorFinder =
                x -> HTMLElement.findElement(By.xpath("//div[text()='%s']/following-sibling::div".formatted(x)));

        errorFinder.apply(fieldLabel)
                .orElseThrow(()->new IllegalStateException(JsonReader.getExceptionMessage("Element not found")))
                .verify.text(expectedText);
    }
}
