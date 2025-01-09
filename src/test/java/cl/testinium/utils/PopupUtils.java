package cl.testinium.utils;

import com.gbursali.elements.HTMLElement;
import org.apache.logging.log4j.LogManager;
import org.awaitility.Awaitility;
import org.openqa.selenium.By;

import java.time.Duration;

public class PopupUtils {

    public static void waitForClosure() {
        Awaitility.await("Waiting for popup to be closed")
                .during(Duration.ofSeconds(1))
                .until(()->!HTMLElement.findElement(By.cssSelector( "div[role=\"presentation\"]"))
                        .map(HTMLElement::isExist)
                        .orElse(false));
        LogManager.getLogger().info("Popup is closed.");
    }
}
