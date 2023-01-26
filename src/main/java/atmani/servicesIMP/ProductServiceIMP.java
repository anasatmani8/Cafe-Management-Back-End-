package atmani.servicesIMP;


import java.io.IOException;
import java.util.ArrayList;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

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
		System.out.println(requestMap.get("file")+"//////////////////////");
		
		product.setImage(requestMap.get("file"));
		
		
		
		product.setDescription(requestMap.get("description"));
		product.setCategory(category);
		product.setPrice(Integer.parseInt(requestMap.get("price")));
		return product;
	}

	@Override
	public ResponseEntity<List<Product>> getAllProduct() {
		try {
			return new ResponseEntity<>(productDao.getAllProducts(), HttpStatus.OK);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

/*	@Override
	public ResponseEntity<String> updateProduct(Map<String, String> requrstMap, MultipartFile[] file) {
		try {
			if (customerUsersDetailsService.getUserDetail().getRole().equalsIgnoreCase("admin")) {
				if (validateProductMap(requrstMap, true)) {
					Optional<Product> product = productDao.findById(Integer.parseInt(requrstMap.get(("id"))));
					if (product.isPresent() == true) {
						Product productN = getProductMap(requrstMap, true, file);
						productN.setStatus(product.get().getStatus());
						productDao.save(productN);
						return CafeUtils.getResponseEntity("Product Updated Successfuly", HttpStatus.OK);
					} else {
						return CafeUtils.getResponseEntity("Product not found :/", HttpStatus.OK);
					}
				} else {
					return CafeUtils.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
				}
			}
			return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}*/

	@Override
	public ResponseEntity<String> deleteProduct(Integer id) {
		System.out.println("start2");
		try {
			if (customerUsersDetailsService.getUserDetail().getRole().equalsIgnoreCase("admin")) {
				Optional<Product> optional = productDao.findById(id);
				if (optional.isPresent() == true) {
					System.out.println("prod found");
					productDao.deleteById(id);
					return CafeUtils.getResponseEntity("Product deleted successfully", HttpStatus.OK);
				} else {
					return CafeUtils.getResponseEntity("Product id not found :/", HttpStatus.OK);
				}
			} else {
				return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> updateStatus(Map<String, String> requestMap) {
		try {
			if (customerUsersDetailsService.getUserDetail().getRole().equalsIgnoreCase("admin")) {
				Optional<Product> optional = productDao.findById(Integer.parseInt(requestMap.get("id")));
				if (optional.isPresent() == true) {
					productDao.updateStatus(requestMap.get("status"), Integer.parseInt(requestMap.get("id")));
					return CafeUtils.getResponseEntity("Product Status updated successfully", HttpStatus.OK);
				} else {
					return CafeUtils.getResponseEntity("Product id not found :/", HttpStatus.OK);
				}
			} else {
				return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<List<Product>> getProdsByCategory(Integer id) {
		try {
			return new ResponseEntity<>(productDao.getProdsByCat(id), HttpStatus.OK);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<List<Product>> getProdsById(Integer id) {
		try {
			return new ResponseEntity<>(productDao.getProdsById(id), HttpStatus.OK);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<List<String>> getIdProductByCat(Integer id) {
		try {
			return new ResponseEntity<List<String>>(productDao.getIdProdsByCat(id), HttpStatus.OK);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
