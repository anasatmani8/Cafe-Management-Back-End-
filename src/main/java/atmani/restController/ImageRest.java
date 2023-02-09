package atmani.restController;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping(path = "/image")
public interface ImageRest {

	@PostMapping(path="/add" ,consumes = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<String> addImage(@RequestBody(required = true) Map<String, String> requestMap, @RequestParam("file") MultipartFile file);

}
