package cl.testinium.base;

import cl.testinium.utils.DriverSettings;
import org.openqa.selenium.WebDriver;

public class Driver {
    private static WebDriver driver;

    private Driver() {
    }

    public static WebDriver getDriver(String browser) {
        if (driver == null) {
            driver = DriverSettings.getDriver(browser);
            Pages.refreshPages();
        }
        return driver;
    }

    public static WebDriver getDriver() {
        return getDriver(null);
    }

    public static void closeDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
