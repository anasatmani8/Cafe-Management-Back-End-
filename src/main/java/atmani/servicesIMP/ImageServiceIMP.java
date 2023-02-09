package atmani.servicesIMP;

import java.io.File;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import atmani.constents.CafeConstants;
import atmani.dao.ImageDao;
import atmani.model.Image;
import atmani.services.ImageService;
import atmani.utils.CafeUtils;
@Service 
public class ImageServiceIMP implements ImageService {
	@Autowired 
	ServletContext context;
	@Autowired
	ImageDao dao;

	@SuppressWarnings("unused")
	@Override
	public ResponseEntity<String> addImage(Map<String, String> requestMap, MultipartFile file) throws JsonParseException, Exception{
		// TODO Auto-generated method stub
		System.out.println("ok");
		Image image = new Image();
		boolean isExist = new File(context.getRealPath("/Images/")).exists();
		if (isExist) {
			new File(context.getRealPath("/Images/")).mkdir();
			System.out.println("--------------- mkdir");
		}
		String fileName = file.getOriginalFilename();
		String newFileName = FilenameUtils.getBaseName(fileName)+"."+FilenameUtils.getExtension(fileName);
		File serverFilename = new File(context.getRealPath("/Images/"+File.separator+newFileName));
		try {
			System.out.println("image");
			FileUtils.writeByteArrayToFile(serverFilename, file.getBytes());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
		image.setName(requestMap.get("name"));
		image.setFileName(newFileName);
		if (image != null) {
			return CafeUtils.getResponseEntity("Image Added Successfully", HttpStatus.OK);
		}else {

			return CafeUtils.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
		}
	}

}
