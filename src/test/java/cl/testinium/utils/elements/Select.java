package cl.testinium.utils.elements;

import cl.testinium.utils.JsonReader;
import com.gbursali.elements.HTMLElement;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ByChained;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class Select extends HTMLElement {
	public Select(By by) {
		super(by);
	}

	private By chainToDropdown(By... chain) {
		var params = Stream.concat(
				Stream.of(locator),
				Stream.of(chain)
		).toArray(By[]::new);
		return new ByChained(params);
	}

	public Select openDropdown() {
		this.asElement().click();
		return this;
	}

	public List<WebElement> getOptions() {
		return chainToDropdown(By.cssSelector("option")).findElements(driver);
	}

	public Optional<HTMLElement> getOption(String text) {
		return HTMLElement.findElement(chainToDropdown(By.xpath("//option[text()='%s']".formatted(text))));
	}

	public Optional<HTMLElement> getOptionContains(String text) {
		return HTMLElement.findElement(chainToDropdown(By.xpath("//option[contains(text(),'%s')]".formatted(text))));
	}

	public void select(String text) {
		this.click();
		this.getOption(text)
				.map(x->x.waitFor.clickability())
				.orElseThrow(() -> new NoSuchElementException(JsonReader.getExceptionMessage("Dropdown option not found",text)))
				.click();
	}

	public void selectContains(String text) {
		this.click();
		this.getOptionContains(text)
				.map(x->x.waitFor.clickability())
				.orElseThrow(() -> new NoSuchElementException(JsonReader.getExceptionMessage("Dropdown option not found",text)))
				.click();
	}

}
