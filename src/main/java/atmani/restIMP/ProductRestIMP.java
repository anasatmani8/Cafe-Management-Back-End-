package atmani.restIMP;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import atmani.constents.CafeConstants;
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

}
