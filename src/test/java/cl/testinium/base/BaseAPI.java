package cl.testinium.base;

import cl.testinium.utils.JsonReader;
import com.gbursali.endpoint.EndpointBase;
import com.google.gson.JsonObject;
import io.restassured.http.Header;
import io.restassured.path.json.JsonPath;

import java.nio.file.Path;

public class BaseAPI {

	public static String apiKey = null;
	private static final Path BASE_FILE_PATH = Path.of("src", "test", "resources", "api-body");

	public static EndpointBase getBase() {
		return EndpointBase.builder(JsonReader.settings().get("api-url").getAsString())
				.withHeader(new Header("content-type", "application/json"))
				.withJsonBasePath(BASE_FILE_PATH);
	}

	public static JsonObject getAuthInfo() {
		var jsonObject = new JsonObject();
		jsonObject.addProperty("username", JsonReader.settings().get("username").getAsString());
		jsonObject.addProperty("password", JsonReader.settings().get("password").getAsString());
		return jsonObject;
	}

	public static String getApiKey() {
		if (apiKey == null) {
			apiKey = getBase()
					.makeWithJson("auth.json")
					.setBody(getAuthInfo().toString())
					.send()
					.jsonPath()
					.getString("access_token");
		}
		return apiKey;
	}

	public static EndpointBase getAuthenticatedBase(){
		Header authorization = new Header("Authorization", "Bearer " + getApiKey());
		return getBase().withHeader(authorization);
	}

}
