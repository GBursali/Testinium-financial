package cl.testinium.utils;

import com.google.gson.JsonObject;

public class AuthUtils {
    public static JsonObject getAuthInfo() {
        var jsonObject = new JsonObject();
        jsonObject.addProperty("username", JsonReader.credentials().get("username").getAsString());
        jsonObject.addProperty("password", JsonReader.credentials().get("password").getAsString());
        return jsonObject;
    }
}
