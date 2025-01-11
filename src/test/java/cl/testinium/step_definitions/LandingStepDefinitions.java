package cl.testinium.step_definitions;

import cl.testinium.base.Pages;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LandingStepDefinitions {
    @Given("I log in to the system")
    public void iLogInToTheSystem() {
        Pages.LANDING_PAGE.login();
    }

    @When("I click on the logout button")
    public void iClickOnTheLogoutButton() {
        Pages.HOMEPAGE.logout();
    }

    @Then("verify that I am on the login page")
    public void verifyThatIAmOnTheLoginPage() {
        Pages.LANDING_PAGE.verifyPage();
    }

    @When("I log in to the system with the wrong password")
    public void iLogInToTheSystemWithTheWrongPassword() {
        Pages.LANDING_PAGE.loginWrongPassword();
    }
}
