package atmani.restIMP;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import atmani.constents.CafeConstants;
import atmani.restController.ImageRest;
import atmani.services.ImageService;
import atmani.utils.CafeUtils;

@RestController
@CrossOrigin("*")
public class ImageRestIMP  {
	
	@Autowired
	ImageService imageService;

	
	public ResponseEntity<String> addIdmage(Map<String, String> requestMap, MultipartFile file) {
		try {
			return imageService.addImage(requestMap, file);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
