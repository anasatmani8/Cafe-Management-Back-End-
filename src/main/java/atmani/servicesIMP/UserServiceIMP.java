package atmani.servicesIMP;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import atmani.constents.CafeConstants;
import atmani.services.UserService;
import atmani.utils.CafeUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j //Login purpose
@Service
public class UserServiceIMP implements UserService {

	@Override
	public ResponseEntity<String> signUp(Map<String, String> requestMap) {
		// TODO Auto-generated method stub
		System.out.printf("Inside signup", requestMap);
		if (validateSignUp(requestMap)) {
			
		}
		return CafeUtils.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
	}
	
	@SuppressWarnings("unused")
	private boolean validateSignUp(Map<String, String> requestMap) {
		if (requestMap.containsKey("name") && requestMap.containsKey("contactnumber") && requestMap.containsKey("email")
				&& requestMap.containsKey("password")) {
			return true;
		}
		return false;
	}

}
