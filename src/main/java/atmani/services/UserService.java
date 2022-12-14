package atmani.services;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import atmani.wrapper.UserWrapper;

public interface UserService {

	
	ResponseEntity<String> signUp(Map<String, String> requestMap);
	
	ResponseEntity<String> login(Map<String, String> requestMap);
	
	ResponseEntity<List<UserWrapper>> getAllUser();

}
