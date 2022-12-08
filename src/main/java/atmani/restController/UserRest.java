package atmani.restController;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path="/user")
public interface UserRest {
	
	@PostMapping(path="/signup")
	public ResponseEntity<String> Signup(@RequestBody(required = true)Map<String, String> requestMap);

}