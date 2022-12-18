package atmani.servicesIMP;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import atmani.JWT.CustomerUsersDetailsService;
import atmani.JWT.JwtFilter;
import atmani.JWT.JwtUtil;
import atmani.constents.CafeConstants;
import atmani.dao.CategoryDao;
import atmani.model.Category;
import atmani.services.CategoryService;
import atmani.utils.CafeUtils;
import atmani.utils.EmailUtils;

@Service
public class CategoryServiceIMP implements CategoryService {

	@Autowired
	CategoryDao categoryDao;

	@Autowired
	CustomerUsersDetailsService customerUsersDetailsService;

	@Autowired
	JwtUtil jwtUtil;

	@Autowired
	JwtFilter jwtFilter;

	@Autowired
	EmailUtils emailUtils;

	Logger log = (Logger) LoggerFactory.getLogger(UserServiceIMP.class);

	@Override
	public ResponseEntity<String> addNewCategory(Map<String, String> requestMap) {
		try {
			System.out.println("new add");
			System.out.println(customerUsersDetailsService.getUserDetail().getRole().equalsIgnoreCase("admin"));
			if (customerUsersDetailsService.getUserDetail().getRole().equalsIgnoreCase("admin")) {
				if (validateCategoryMap(requestMap, false)) {
					System.out.println("1");
					categoryDao.save(getCategoryMap(requestMap, false));
					return CafeUtils.getResponseEntity("Category added successfully ", HttpStatus.OK);
				}
			} else {
				return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private boolean validateCategoryMap(Map<String, String> requestMap, boolean validateId) {
		if (requestMap.containsKey("name")) {
			if (requestMap.containsKey("id") && validateId) {
				return true;
			} else if (!validateId)
				return true;
		}
		return false;
	}

	private Category getCategoryMap(Map<String, String> requestMap, Boolean isAdd) {
		Category category = new Category();
		if (isAdd) {
			category.setId(Integer.parseInt(requestMap.get("id")));
		}
		category.setName(requestMap.get("name"));
		System.out.println(requestMap.get("name"));
		return category;
	}

	@Override
	public ResponseEntity<List<Category>> getAllCategory() {
		try {
			return new ResponseEntity<List<Category>>(categoryDao.findAll(), HttpStatus.OK);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
