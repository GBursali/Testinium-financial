package cl.testinium.base;

import cl.testinium.pages.*;

public class Pages {
	public static final HomePage HOMEPAGE = new HomePage();
	public static final MoneyTransferPage MONEY_TRANSFER_PAGE = new MoneyTransferPage();
	public static final LandingPage LANDING_PAGE = new LandingPage();
	public static final AddMoneyPopup ADD_MONEY_POPUP = new AddMoneyPopup();

	public static class Popups{
		public static final TransferMoneyPopup MONEY_TRANSFER_POPUP = new TransferMoneyPopup();
	}
}
