package atmani.services;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import atmani.model.Category;

public interface CategoryService {

	ResponseEntity<String> addNewCategory(Map<String, String> requestMap);

	ResponseEntity<List<Category>> getAllCategory();

	ResponseEntity<String> updateCategery(Map<String, String> requestMap);

}
