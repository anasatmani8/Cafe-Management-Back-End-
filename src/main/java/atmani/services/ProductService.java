package atmani.services;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import atmani.wrapper.ProductWrapper;

public interface ProductService {

	ResponseEntity<String> addProduct(Map<String, String> requestMap);

	ResponseEntity<List<Object>> getAllProduct();

}
