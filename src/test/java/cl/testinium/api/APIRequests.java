package cl.testinium.api;

import cl.testinium.base.BaseAPI;

public class APIRequests extends BaseAPI {
	public static void topup(){
		getAuthenticatedBase()
				.makeWithJson("addMoney.json")
				.send();
	}
}
