package cl.testinium.runners;


import cl.testinium.api.APIRequests;
import cl.testinium.base.BaseAPI;
import cl.testinium.utils.JsonReader;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.restassured.http.Header;
import io.restassured.http.Method;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
		monochrome = true,
		plugin = {
				"json:target/cucumber.json",
				"html:target/default-cucumber-reports"
		},
		tags ="",
		features = {"src/test/resources/features"},
		glue = {"cl/testinium/step_definitions"}

)
public class CukesRunner {
	public static void main(String[] args) {
		APIRequests.topup();
	}
}
