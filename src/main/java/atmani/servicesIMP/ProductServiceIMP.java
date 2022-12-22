package atmani.servicesIMP;

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
import atmani.dao.ProductDao;
import atmani.model.Category;
import atmani.model.Product;
import atmani.services.ProductService;
import atmani.utils.CafeUtils;
import atmani.utils.EmailUtils;

@Service
public class ProductServiceIMP implements ProductService {

	@Autowired
	ProductDao productDao;

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
	public ResponseEntity<String> addProduct(Map<String, String> requestMap) {
		try {
			if (customerUsersDetailsService.getUserDetail().getRole().equalsIgnoreCase("admin")) {
				if (validateProductMap(requestMap, false)) {
					productDao.save(getProductMap(requestMap, false));
					return CafeUtils.getResponseEntity("Product Added Successfully", HttpStatus.OK);
				} else {
					return CafeUtils.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
				}
			} else {
				return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private boolean validateProductMap(Map<String, String> requestMap, boolean validateId) {
		if (requestMap.containsKey("name")) {
			if (requestMap.containsKey("id") && validateId) {
				return true;
			} else if (!validateId)
				return true;
		}
		return false;
	}

	private Product getProductMap(Map<String, String> requestMap, Boolean isAdd) {
		Category category = new Category();
		category.setId(Integer.parseInt(requestMap.get("categoryId")));

		Product product = new Product();
		if (isAdd) {
			product.setId(Integer.parseInt(requestMap.get("id")));
		} else {
			product.setStatus("true");
		}
		product.setName(requestMap.get("name"));
		product.setDescription(requestMap.get("description"));
		product.setCategory(category);
		product.setPrice(Integer.parseInt(requestMap.get("price")));
		return product;
	}

}
