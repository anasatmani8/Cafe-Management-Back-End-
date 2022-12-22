package atmani.services;

import java.util.Map;

import org.springframework.http.ResponseEntity;

public interface ProductService {

	ResponseEntity<String> addProduct(Map<String, String> requestMap);

}
