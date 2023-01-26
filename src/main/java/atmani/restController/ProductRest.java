package atmani.restController;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;
import atmani.model.Product;

@RequestMapping(path = "/product")
public interface ProductRest {
	
	@PostMapping(path="/add")
	ResponseEntity<String> addProduct(@RequestBody(required = true) Map<String, String> requestMap);
	
	@GetMapping(path="/get")
	ResponseEntity<List<Product>>getAllProduct();
	
	/*@PostMapping(path="/update")
	ResponseEntity<String> updateProduct(@RequestBody(required = true) Map<String, String> requrstMap, @RequestParam("image") MultipartFile file);
	*/
	@GetMapping(path="/delete/{id}")
	ResponseEntity<String> deleteProduct(@PathVariable Integer id);
	
	@PostMapping(path="/updateStatus")
	ResponseEntity<String> updateStatus(@RequestBody(required = true) Map<String, String> requestMap);
	
	@GetMapping(path="/getByCategory/{id}")
	ResponseEntity<List<Product>>getProductsByCategory(@PathVariable Integer id);
	
	@GetMapping(path="/getById/{id}")
	ResponseEntity<List<Product>>getProductsById(@PathVariable Integer id);
	
	@GetMapping(path="/getIdProductByCat/{id}")
	ResponseEntity<List<String>>getIdProductByCat(@PathVariable Integer id);

}
