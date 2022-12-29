package atmani.utils;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.google.common.base.Strings;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import atmani.servicesIMP.UserServiceIMP;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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

	public static Boolean isFileExist(String path) {
		Logger log = (Logger) LoggerFactory.getLogger(UserServiceIMP.class);
		System.out.println(path);
		log.info("inside ifFileExist {}", path);
		try {
			File file = new File(path);
			return (file != null && file.exists()) ? Boolean.TRUE : Boolean.FALSE;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return false;
	}
}
