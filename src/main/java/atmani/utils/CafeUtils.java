package atmani.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.google.common.base.Strings;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

public class CafeUtils {

	private CafeUtils() {

	}

	public static ResponseEntity<String> getResponseEntity(String responseMessage, HttpStatus httpStatus) {
		return new ResponseEntity<String>("{\"message\":\"" + responseMessage + "\"}", httpStatus);
	}

	public static String getUUID() {
		Date date = new Date();
		long time = date.getTime();
		return "Bill-" + time;
	}

	public static JSONArray getJsonDataFromString(String data) throws JSONException {
		JSONArray array = new JSONArray(data);
		return array;
	}

	@SuppressWarnings("serial")
	public static Map<String, Object> GetMapFromJson(String data) {
		if (!Strings.isNullOrEmpty(data)) {
			return new Gson().fromJson(data, new TypeToken<Map<String, Object>>() {
			}.getType());
		}
		return new HashMap<>();
	}
}
