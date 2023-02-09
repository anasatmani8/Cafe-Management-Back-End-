package atmani.services;

import java.util.Map;

import org.springframework.boot.json.JsonParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

	ResponseEntity<String> addImage(Map<String, String> requestMap, MultipartFile file) throws JsonParseException, Exception;

}
