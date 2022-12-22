package atmani.restController;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = "/product")
public interface ProductRest {
	
	@PostMapping(path="/add")
	ResponseEntity<String> addProduct(@RequestBody(required = true) Map<String, String> requestMap);

}
