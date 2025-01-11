package cl.testinium.pages;

import cl.testinium.utils.JsonReader;
import com.gbursali.elements.HTMLElement;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.util.Map;
import java.util.function.Function;

public class HomePage {

    @FindBy(xpath = "//div[./div[text()='Open Money Transfer']]")
    public HTMLElement openMoneyTransfer;
    @FindBy(xpath = "//div[text()='Logout']")
    public HTMLElement logout;

    public void openMoneyTransferPage() {
        openMoneyTransfer.waitFor.clickability().click();
    }

    public void verifyHomepageData(Map<String, String> data) {
        final Function<String, String> landingDataFinder =
                label -> HTMLElement.findElement(By.xpath("//div[./span[text()='%s']]".formatted(label)))
                        .orElseThrow(()->new IllegalArgumentException(JsonReader.getExceptionMessage("Element not found")))
                        .getText();
        final String label = data.get("Label");
        final var expectedText = data.get("Value");

        var actualText = landingDataFinder.apply(label)
                .replace(label,"")
                .trim();
        Assert.assertEquals("%s's value is not %s".formatted(label,expectedText),expectedText,actualText);
    }

    public void verifyHomePage() {
        openMoneyTransfer.waitFor.clickability();
    }

    public void logout(){
        logout.waitFor.clickability().click();
    }
}
