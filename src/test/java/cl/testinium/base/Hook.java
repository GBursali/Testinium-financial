package cl.testinium.base;

import cl.testinium.utils.JsonReader;
import com.google.gson.JsonObject;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.awaitility.Awaitility;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class Hook {
	double startTime;
	double duration;
	public static Map<String, byte[]> screenshots = new HashMap<>();

	/**
	 * Takes a screenshot of the current page
	 */
	public static void takeScreenShot(String name) {
		var ssTaker = (TakesScreenshot) Driver.getDriver();
		screenshots.put(name, ssTaker.getScreenshotAs(OutputType.BYTES));
	}

	//default HOOK runs for any scenario
	@Before
	public void setup(Scenario scenario){
		//Clear the saved screenshots
		screenshots = new HashMap<>();
		Awaitility.reset();
		Awaitility.setDefaultTimeout(Duration.ofSeconds(100));
		Awaitility.setDefaultPollInterval(Duration.ofMillis(100));
		LogManager.getLogger().info("Starting the scenario {}", scenario.getName());
		startTime = System.nanoTime();
		JsonObject settings = JsonReader.settings();
		String browser = settings.get("browser").getAsString();
		String url = settings.get("url").getAsString();

		Driver.getDriver(browser).get(url);
	}

	@After
	public void teardown(Scenario scenario){
		if(scenario.isFailed()) {
			takeScreenShot("SS before failure");
		}
		screenshots.forEach((name, image) -> {
			scenario.attach(image, "image/png", name);
		});

//		scenario.attach(dynamicIdentifiers,"text/plain","Dynamic Variables");
		Driver.closeDriver();
		duration = (System.nanoTime() - startTime) / 1000000000;
		LogManager.getLogger().info("Scenario {} in {} seconds",scenario.getStatus().name(),duration);
	}
}
