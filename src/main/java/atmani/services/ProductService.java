package atmani.services;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import atmani.model.Product;



public interface ProductService {

	ResponseEntity<String> addProduct(Map<String, String> requestMap);

	ResponseEntity<List<Product>> getAllProduct();

	ResponseEntity<String> updateProduct(Map<String, String> requrstMap);

	ResponseEntity<String> deleteProduct(Integer id);

	ResponseEntity<String> updateStatus(Map<String, String> requestMap);

	ResponseEntity<List<Product>> getProdsByCategory(Integer id);

	ResponseEntity<List<Product>> getProdsById(Integer id);

}
