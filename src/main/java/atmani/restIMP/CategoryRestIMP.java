package atmani.restIMP;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import atmani.constents.CafeConstants;
import atmani.restController.CategoryRest;
import atmani.services.CategoryService;
import atmani.utils.CafeUtils;

@RestController
public class CategoryRestIMP implements CategoryRest {
	
	@Autowired
	CategoryService categoryService;

	@Override
	public ResponseEntity<String> addNewCategory(Map<String, String> requestMap) {
		System.out.println("RestIMP");
		try { 
			return categoryService.addNewCategory(requestMap);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
