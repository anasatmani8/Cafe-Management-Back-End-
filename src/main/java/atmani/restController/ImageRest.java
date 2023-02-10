package atmani.restController;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import atmani.dao.ImageDao;
import atmani.model.Image;
import atmani.utils.CafeUtils;

@RequestMapping(path = "/image")
@RestController
@CrossOrigin("*")
public class ImageRest {
	@Autowired 
	ServletContext context;
	@Autowired
	ImageDao dao;

	
	@PostMapping(path="/add"  )
	ResponseEntity<String> addImage(@RequestBody(required = true) Map<String, String> requestMap, @RequestParam("file") MultipartFile file) throws IOException {
		System.out.println("ok");
		Image image = new Image();
		boolean isExist = new File(context.getRealPath("/Images/")).exists();
		if (isExist) {
			System.out.println("makaynch");
			new File(context.getRealPath("/Images/")).mkdir();
			System.out.println("--------------- mkdir");
		}
		System.out.println("insertion d fichier");
		String fileName = file.getOriginalFilename();
		String newFileName = FilenameUtils.getBaseName(fileName)+"."+FilenameUtils.getExtension(fileName);
		File serverFilename = new File(context.getRealPath("/Images/"+File.separator+newFileName));
		System.out.println(serverFilename+" name");
		try {
			System.out.println("image");
			FileUtils.writeByteArrayToFile(serverFilename, file.getBytes());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
		image.setName(requestMap.get("name"));
		image.setFileName(newFileName);
		Image im = dao.save(image);
		if (im != null) {
			return CafeUtils.getResponseEntity("Image Added Successfully", HttpStatus.OK);
		}else {

			return CafeUtils.getResponseEntity("Image not saved", HttpStatus.BAD_REQUEST);
		}
	}

}
