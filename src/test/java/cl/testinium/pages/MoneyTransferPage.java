package cl.testinium.pages;

import cl.testinium.api.APIRequests;
import cl.testinium.base.Driver;
import cl.testinium.data.CurrentMoneyData;
import cl.testinium.data.TransferData;
import cl.testinium.utils.JsonReader;
import com.gbursali.data.DataManager;
import com.gbursali.elements.HTMLElement;
import com.machinezoo.noexception.Exceptions;
import org.apache.logging.log4j.LogManager;
import org.awaitility.Awaitility;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

public class MoneyTransferPage {
    public static final DateTimeFormatter TRANSACTION_DATETIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public double getSavedMoney(){
        return DataManager.getFirst(CurrentMoneyData.class)
                .map(x->x.money)
                .orElseThrow();
    }
    @FindBy(xpath = "//div[./div[text()='Amount']]//div[./following-sibling::*[name()='svg']]")
    public HTMLElement accountBalance;

    @FindBy(xpath = "//div[./div[text()='Transfer money']]")
    public HTMLElement transferMoneyButton;

    @FindBy(xpath = "//div[text()='Transactions']/following-sibling::div[1]//div[@tabindex='0']")
    public HTMLElement div_TransactionsList;


    public double getAccountBalance() {
        NumberFormat formatter = NumberFormat.getInstance();
        return Exceptions
                .wrap(e -> new IllegalStateException(JsonReader.getExceptionMessage("Balance is not a valid number"), e))
                .get(() -> formatter.parse(accountBalance.waitFor.existence().getText()).doubleValue());
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

    public void verifyTransfer() {
        var lastTransfer = DataManager.getFirst(TransferData.class)
                .orElseThrow(() -> new RuntimeException(JsonReader.getExceptionMessage("No transfers found")));
        verifyTransfer(lastTransfer);
        var expectedMoney = DecimalFormat.getInstance().format(getSavedMoney()-lastTransfer.amount);
        var actualMoney = DecimalFormat.getInstance().format(getAccountBalance());
        Assert.assertEquals(expectedMoney, actualMoney);
    }

    private void verifyTransfer(TransferData lastTransfer) {
        Awaitility.await().timeout(Duration.ofSeconds(3)).until(() -> div_TransactionsList.isExist());
        var transaction = extractTransaction(div_TransactionsList.asElement());
        Assert.assertEquals(lastTransfer.senderAccount,transaction.senderAccount);
        Assert.assertEquals(lastTransfer.receiverAccount,transaction.receiverAccount);
        //ignore seconds
        Assert.assertEquals(lastTransfer.sendingDate.withSecond(0).withNano(0),transaction.sendingDate.withSecond(0).withNano(0));
        Assert.assertEquals(lastTransfer.amount,transaction.amount,0);
    }

    private TransferData extractTransaction(WebElement webElement) {
        final Function<String, By> subLocatorGenerator = x -> By.xpath("//div[normalize-space(.)=\"%s:\"]/following-sibling::div".formatted(x));
        var data = new TransferData(null);
        WebElement senderElement = webElement.findElement(subLocatorGenerator.apply("Sender"));
        WebElement receiverElement = webElement.findElement(subLocatorGenerator.apply("Receiver"));
        WebElement timeElement = webElement.findElement(subLocatorGenerator.apply("Time"));
        WebElement dateElement = webElement.findElement(subLocatorGenerator.apply("Amount"));
        data.senderAccount = senderElement.getText();
        data.receiverAccount = receiverElement.getText();
        data.sendingDate = LocalDateTime.parse(timeElement.getText(),TRANSACTION_DATETIME_FORMATTER);
        data.amount = Exceptions
                .wrap(e -> new IllegalStateException(JsonReader.getExceptionMessage("Balance is not a valid number"), e))
                .get(() -> NumberFormat.getInstance().parse(dateElement.getText()).doubleValue());

        return data;
    }
}
