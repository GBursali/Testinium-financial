package cl.testinium.pages;

import cl.testinium.utils.AuthUtils;
import com.gbursali.elements.HTMLElement;
import com.gbursali.elements.Textbox;
import org.awaitility.Awaitility;
import org.junit.Assert;
import org.openqa.selenium.support.FindBy;

public class LandingPage {
    @FindBy(css = "img[alt]")
    public HTMLElement logo;
    @FindBy(css = "input[placeholder='Username']")
    public Textbox username;
    @FindBy(css = "input[placeholder='Password']")
    public Textbox password;
    @FindBy(xpath = "//div[./div[text()='Login']]")
    public HTMLElement loginButton;

    public void login(){
        username.sendKeys(AuthUtils.getAuthInfo().get("username").getAsString());
        password.sendKeys(AuthUtils.getAuthInfo().get("password").getAsString());
        loginButton.waitFor.clickability().click();
    }

    public void loginWrongPassword(){
        username.sendKeys(AuthUtils.getAuthInfo().get("username").getAsString());
        password.sendKeys("wrongPassword");
        loginButton.waitFor.clickability().click();
    }

    public void verifyPage() {
        Assert.assertTrue(logo.isExist());
        Assert.assertTrue(username.isExist());
        Assert.assertTrue(password.isExist());
        Assert.assertTrue(loginButton.isExist());
    }
}
