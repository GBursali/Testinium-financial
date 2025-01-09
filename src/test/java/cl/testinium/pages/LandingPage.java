package cl.testinium.pages;

import cl.testinium.base.Driver;
import cl.testinium.utils.AuthUtils;
import com.gbursali.elements.HTMLElement;
import com.gbursali.elements.Textbox;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LandingPage {
    @FindBy(css = "input[placeholder='Username']")
    public Textbox username;
    @FindBy(css = "input[placeholder='Password']")
    public Textbox password;
    @FindBy(xpath = "//div[./div[text()='Login']]")
    public HTMLElement loginButton;

    public LandingPage(){
        PageFactory.initElements(HTMLElement.getDecorator(Driver.getDriver()),this);
    }

    public void login(){
        username.sendKeys(AuthUtils.getAuthInfo().get("username").getAsString());
        password.sendKeys(AuthUtils.getAuthInfo().get("password").getAsString());
        loginButton.waitFor.clickability().click();
    }
}
