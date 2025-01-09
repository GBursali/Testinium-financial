package cl.testinium.utils;

import com.google.gson.*;
import com.machinezoo.noexception.Exceptions;
import org.apache.logging.log4j.LogManager;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class JsonReader {
	private static final Gson gson = new GsonBuilder().setStrictness(Strictness.LENIENT).create();
	private static final Path BASE_FILE_PATH = Path.of("src","test","resources","json");
	private static final Map<String,JsonObject> cache = new ConcurrentHashMap<>();

	public static JsonObject settings(){
		return cache.computeIfAbsent("settings", key -> read("settings"));
	}
	public static JsonObject credentials(){
		return cache.computeIfAbsent("credentials", key -> read("credentials"));
	}
	public static JsonObject exceptionMessages(){
		return cache.computeIfAbsent("exception-messages", x->read("exception-messages"));
	}
	public static String getExceptionMessage(String keyword){
		return exceptionMessages().get(keyword).getAsString();
	}
	private static JsonObject read(String keyword){
		var filename = keyword;
		if(!filename.contains(".json"))
			filename += ".json";
		var filepath = BASE_FILE_PATH.resolve(filename).toAbsolutePath();
		LogManager.getLogger().info("Reading " + filepath);
		var contents =  Exceptions.sneak().get(()->Files.readString(filepath));
		return gson.fromJson(contents, JsonObject.class);
	}

}
