package cl.testinium.base;

import cl.testinium.pages.*;
import com.gbursali.elements.HTMLElement;
import com.machinezoo.noexception.Exceptions;
import org.openqa.selenium.support.PageFactory;

import java.util.Arrays;

public class Pages {
	public static final HomePage HOMEPAGE = new HomePage();
	public static final MoneyTransferPage MONEY_TRANSFER_PAGE = new MoneyTransferPage();
	public static final LandingPage LANDING_PAGE = new LandingPage();
	public static final TransferMoneyPopup MONEY_TRANSFER_POPUP = new TransferMoneyPopup();
	public static final AddMoneyPopup ADD_MONEY_POPUP = new AddMoneyPopup();

	public static void refreshPages(){
		Arrays.stream(Pages.class.getFields())
				.map(x-> Exceptions.wrap().get(()->x.get(null)))
				.forEach(x-> PageFactory.initElements(HTMLElement.getDecorator(Driver.getDriver()),x));
	}
}
