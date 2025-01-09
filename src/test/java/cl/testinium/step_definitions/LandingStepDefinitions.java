package cl.testinium.step_definitions;

import cl.testinium.base.Pages;
import io.cucumber.java.en.Given;

public class LandingStepDefinitions {
    @Given("I log in to the system")
    public void iLogInToTheSystem() {
        Pages.LANDING_PAGE.login();
    }
}
