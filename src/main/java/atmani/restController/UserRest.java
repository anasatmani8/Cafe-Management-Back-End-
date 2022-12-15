package atmani.restController;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import atmani.model.User;


@RequestMapping(path="/user")    
public interface UserRest {
	
	@PostMapping(path="/signup")
	public ResponseEntity<String> Signup(@RequestBody(required = true)Map<String, String> requestMap);
	
	@PostMapping(path="/login")
	public ResponseEntity<String> Login(@RequestBody(required = true) Map<String, String> requestMap);
	
	@GetMapping(path="/get")
	public ResponseEntity<List<User>> getAllUser();

}
