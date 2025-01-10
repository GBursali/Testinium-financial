package cl.testinium.step_definitions;

import cl.testinium.base.Pages;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;

public class HomeStepDefinitions {
    @Then("the page should have the following data:")
    public void thePageShouldHaveTheFollowingData(DataTable data) {
        data.asMaps().forEach(Pages.HOMEPAGE::verifyHomepageData);
    }

    @Then("verify that I am on the home page")
    public void verifyThatIAmOnTheHomePage() {
        Pages.HOMEPAGE.verifyHomePage();
    }
}
