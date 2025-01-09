package cl.testinium.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Driver {

	private static final Logger logger = LogManager.getLogger();
	private static WebDriver driver;
	private Driver() {
	}
	public synchronized static WebDriver getDriver(String browser) {
		if (driver == null) {
			browser = Objects.requireNonNullElse(browser,"chrome");
			logger.info("Creating the driver for: " + browser);
			switch (browser) {
				case "chrome":
					WebDriverManager.chromedriver().setup();
					ChromeOptions options = new ChromeOptions();
					configureChromeOptions(options);
					driver = new ChromeDriver(options);
					logger.info("Created Chrome driver with the following options: " + options.asMap());
					break;
				case "firefox":
					WebDriverManager.firefoxdriver().setup();
					driver = new FirefoxDriver();
					break;
				case "ie":
					if (System.getProperty("os.name").toLowerCase().contains("mac"))
						throw new WebDriverException("You are operating Mac OS which doesn't support Internet Explorer");
					WebDriverManager.iedriver().setup();
					driver = new InternetExplorerDriver();
					break;
				case "edge":
					WebDriverManager.edgedriver().setup();
					EdgeOptions opt = new EdgeOptions();
					opt.addArguments("--start-maximized");
					driver = new EdgeDriver(opt);
					break;
				case "safari":
					if (System.getProperty("os.name").toLowerCase().contains("windows"))
						throw new WebDriverException("You are operating Windows OS which doesn't support Safari");
					WebDriverManager.getInstance(SafariDriver.class).setup();
					driver = new SafariDriver();
					break;
				default:
					throw new IllegalArgumentException("Unknown browser type!");
			}
		}
		return driver;
	}
	public static WebDriver getDriver() {
		return getDriver(null);
	}

	public static JavascriptExecutor getJsExecutor(){
		return (JavascriptExecutor) getDriver();
	}
	public  static void closeDriver() {
		if (driver != null) {
			driver.quit();
			driver = null;
		}
	}
	public static WebDriverWait getWaiter(){
		return getWaiter(500L);
	}
	public static WebDriverWait getWaiter(long timeout) {
		return new WebDriverWait(getDriver(), Duration.ofSeconds(timeout));
	}
	public static Actions getAction(){
		return new Actions(Driver.getDriver());
	}

	private static void configureChromeOptions(ChromeOptions options){
		final Map<String,Object> prefs = new HashMap<>();

		prefs.put("safebrowsing.enabled", "false"); // Bypass warning message, keep file anyway (for .exe, .jar, etc.)
		prefs.put("profile.default_content_settings_.popups",0);
		prefs.put("download.prompt_for_download", "false");
		prefs.put("profile.default_content_settings.popups", 0);
//		options.setCapability(CapabilityType.UNHANDLED_PROMPT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
		options.addArguments("--safebrowsing-disable-extension-blacklist");
		options.addArguments("--safebrowsing-disable-download-protection");
		options.addArguments("--start-maximized");
		options.addArguments("enable-automation");
		options.addArguments("--disable-crash-reporter");
		options.addArguments("--disable-client-side-phishing-detection");
		options.addArguments("--disable-extensions");
		options.addArguments("--allow-running-insecure-content");
		options.setExperimentalOption("prefs", prefs);
	}
}
