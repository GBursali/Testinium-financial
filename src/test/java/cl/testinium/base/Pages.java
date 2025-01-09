package cl.testinium.base;

import cl.testinium.pages.HomePage;
import cl.testinium.pages.LandingPage;
import cl.testinium.pages.MoneyTransferPage;
import cl.testinium.pages.TransferMoneyPopup;

public class Pages {
	public static final HomePage HOMEPAGE = new HomePage();
	public static final MoneyTransferPage MONEY_TRANSFER_PAGE = new MoneyTransferPage();
	public static final LandingPage LANDING_PAGE = new LandingPage();

    public static class Popups{
		public static final TransferMoneyPopup MONEY_TRANSFER_POPUP = new TransferMoneyPopup();
	}
}
