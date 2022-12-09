package atmani.servicesIMP;

import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import atmani.constents.CafeConstants;
import atmani.dao.UserDao;
import atmani.model.User;
import atmani.services.UserService;
import atmani.utils.CafeUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j // Login purpose
@Service
public class UserServiceIMP implements UserService {

	@Autowired
	UserDao userDao;

	@Override
	public ResponseEntity<String> signUp(Map<String, String> requestMap) {
		// TODO Auto-generated method stub
		System.out.printf("Inside signup / ", requestMap);
		
		try { 
		if (validateSignUp(requestMap)) {
			User user = userDao.findByEmail(requestMap.get("email"));
			if (Objects.isNull(user)) {
				userDao.save(getUserFromMap(requestMap));
				return CafeUtils.getResponseEntity("Successfully registred", HttpStatus.OK);
			} else {
				return CafeUtils.getResponseEntity("Email already exits", HttpStatus.BAD_REQUEST);
			}

		} else {

			return CafeUtils.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
		}
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);

	}

	private boolean validateSignUp(Map<String, String> requestMap) {
		if (requestMap.containsKey("name") && requestMap.containsKey("contactnumber") && requestMap.containsKey("email")
				&& requestMap.containsKey("password")) {
			return true;
		}
		return false;
	}

	private User getUserFromMap(Map<String, String> requeMap) {
		User user = new User();
		user.setName(requeMap.get("name"));
		user.setEmail(requeMap.get("email"));
		user.setConctactnumber(requeMap.get("contactnumber"));
		user.setPassword(requeMap.get("password"));
		user.setStatus(requeMap.get("status"));
		user.setRole(requeMap.get("role"));
		return user;
	}

}
