package cl.testinium.runners;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
		monochrome = true,
		plugin = {
				"json:target/cucumber.json"
		},
		tags ="",
		features = {"src/test/resources/features"},
		glue = {"cl/testinium/step_definitions","cl/testinium/base"}

)
public class FullRunner {
}
