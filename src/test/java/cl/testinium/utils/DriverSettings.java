package cl.testinium.utils;

import com.machinezoo.noexception.Exceptions;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class DriverSettings<T extends WebDriver, U extends MutableCapabilities> {
    public static final Map<String, DriverSettings<? extends WebDriver, ? extends MutableCapabilities>> keywords = new HashMap<>() {{
        put("chrome", new DriverSettings<>(ChromeDriver.class, ChromeOptions.class));
        put("firefox", new DriverSettings<>(FirefoxDriver.class, FirefoxOptions.class));
        put("ie", new DriverSettings<>(InternetExplorerDriver.class, InternetExplorerOptions.class));
        put("edge", new DriverSettings<>(EdgeDriver.class, EdgeOptions.class));
        put("safari", new DriverSettings<>(SafariDriver.class, SafariOptions.class));
    }};

    public static WebDriver getDriver(String keyword) {
        keyword = Optional.ofNullable(keyword)
                .filter(keywords::containsKey)
                .orElse("chrome");
        return keywords.get(keyword).toDriver();
    }

    private final Class<T> driverType;
    private final Class<U> optionType;

    public DriverSettings(Class<T> driverType, Class<U> optionType) {
        this.driverType = driverType;
        this.optionType = optionType;
    }

    public T toDriver() {
        var options = makeOptions();
//        options.setCapability(CapabilityType.UNHANDLED_PROMPT_BEHAVIOUR, UnexpectedAlertBehaviour.DISMISS);
        return makeDriver(options);
    }

    private T makeDriver(U options) {
        return Exceptions.wrap().get(() -> driverType
                .getConstructor(options.getClass())
                .newInstance(options));
    }

    private U makeOptions() {
        return Exceptions.wrap().get(() -> optionType
                .getConstructor()
                .newInstance());
    }

}
