package atmani.restController;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import atmani.wrapper.ProductWrapper;

@RequestMapping(path = "/product")
public interface ProductRest {
	
	@PostMapping(path="/add")
	ResponseEntity<String> addProduct(@RequestBody(required = true) Map<String, String> requestMap);
	
	@GetMapping(path="/get")
	ResponseEntity<List<Object>>getAllProduct();

}
