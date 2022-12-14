package atmani.restIMP;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import atmani.constents.CafeConstants;
import atmani.services.UserService;
import atmani.utils.CafeUtils;
import atmani.wrapper.UserWrapper;


@RestController
public class UserRestIMP implements atmani.restController.UserRest {
	
	@Autowired
	UserService userService;
	
	@Override
	public ResponseEntity<String> Signup(Map<String, String> requestMap) {
		// TODO Auto-generated method stub
		
		try {
			return this.userService.signUp(requestMap);
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> Login(Map<String, String> requestMap) {
		// TODO Auto-generated method stub
		try {
			return userService.login(requestMap);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<List<UserWrapper>> getAllUser() {
		// TODO Auto-generated method stub
		System.out.println("inside rest ");
		try {
			
			return userService.getAllUser();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<List<UserWrapper>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
