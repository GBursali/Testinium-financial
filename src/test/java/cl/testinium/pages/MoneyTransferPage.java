package cl.testinium.pages;

import cl.testinium.api.APIRequests;
import cl.testinium.base.Driver;
import cl.testinium.data.CurrentAccountData;
import cl.testinium.data.TransferData;
import cl.testinium.utils.JsonReader;
import cl.testinium.utils.TextUtils;
import com.gbursali.data.DataManager;
import com.gbursali.elements.HTMLElement;
import org.apache.logging.log4j.LogManager;
import org.awaitility.Awaitility;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

public class MoneyTransferPage {
    public static final DateTimeFormatter TRANSACTION_DATETIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    @FindBy(xpath = "//div[./div[text()='Amount']]//div[./following-sibling::*[name()='svg']]")
    public HTMLElement accountBalance;
    @FindBy(xpath = "//div[text()='Account name']//following-sibling::div[1]")
    public HTMLElement accountName;

    @FindBy(xpath = "//div[./div[text()='Transfer money']]")
    public HTMLElement transferMoneyButton;

    @FindBy(xpath = "//div[text()='Transactions']/following-sibling::div[1]//div[@tabindex='0']")
    public HTMLElement div_LastTransaction;


    public double getSavedMoney() {
        return DataManager.getFirst(CurrentAccountData.class)
                .map(x -> x.money)
                .orElseThrow();
    }

    public double getAccountBalance() {
        return TextUtils.convertToDouble(accountBalance.waitFor.existence().getText());
    }

    public String getAccountName() {
        return accountName.getText();
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
        verifyMoney(-lastTransfer.amount);
    }

    public void verifyTransfer(TransferData lastTransfer) {
        Awaitility.await().timeout(Duration.ofSeconds(3)).until(() -> div_LastTransaction.isExist());
        var transaction = extractTransaction(div_LastTransaction.asElement());
        Assert.assertEquals(lastTransfer.senderAccount, transaction.senderAccount);
        Assert.assertEquals(lastTransfer.receiverAccount, transaction.receiverAccount);
        //ignore seconds
        Assert.assertEquals(lastTransfer.sendingDate.withSecond(0).withNano(0), transaction.sendingDate.withSecond(0).withNano(0));
        Assert.assertEquals(lastTransfer.amount, transaction.amount, 0);
    }

    public void verifyMoney(double expectedEffect) {
        var expectedMoney = TextUtils.formatToText(getSavedMoney() + expectedEffect);
        var actualMoney = TextUtils.formatToText(getAccountBalance());
        Assert.assertEquals(expectedMoney, actualMoney);
    }

    private TransferData extractTransaction(WebElement webElement) {
        final Function<String, By> subLocatorGenerator = x -> By.xpath("//div[normalize-space(.)=\"%s:\"]/following-sibling::div".formatted(x));
        WebElement senderElement = webElement.findElement(subLocatorGenerator.apply("Sender"));
        WebElement receiverElement = webElement.findElement(subLocatorGenerator.apply("Receiver"));
        WebElement timeElement = webElement.findElement(subLocatorGenerator.apply("Time"));
        WebElement dateElement = webElement.findElement(subLocatorGenerator.apply("Amount"));
        return new TransferData(null){{
            senderAccount = senderElement.getText();
            receiverAccount = receiverElement.getText();
            sendingDate = LocalDateTime.parse(timeElement.getText(), TRANSACTION_DATETIME_FORMATTER);
            amount = TextUtils.convertToDouble(dateElement.getText());
        }};
    }
}
