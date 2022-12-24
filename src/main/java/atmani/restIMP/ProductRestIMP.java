package atmani.restIMP;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import atmani.constents.CafeConstants;
import atmani.model.Product;
import atmani.restController.ProductRest;
import atmani.services.ProductService;
import atmani.utils.CafeUtils;

@RestController
public class ProductRestIMP implements ProductRest {

	@Autowired
	ProductService productService;

	@Override
	public ResponseEntity<String> addProduct(Map<String, String> requestMap) {
		try {
			return productService.addProduct(requestMap);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<List<Product>> getAllProduct() {
		try {
			return productService.getAllProduct();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> updateProduct(Map<String, String> requrstMap) {
		try {
			return productService.updateProduct(requrstMap);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> deleteProduct(Integer id) {
		try {
			return productService.deleteProduct(id);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> updateStatus(Map<String, String> requestMap) {
		try {
			return productService.updateStatus(requestMap);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<List<Product>> getProductsByCategory(Integer id) {
		try {
			return productService.getProdsByCategory(id);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<List<Product>> getProductsById(Integer id) {
		try {
			return productService.getProdsById(id);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
