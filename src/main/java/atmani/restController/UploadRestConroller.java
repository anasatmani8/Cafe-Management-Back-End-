package atmani.restController;

import java.io.File;
import java.nio.file.Paths;
import java.util.Iterator;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api")
public class UploadRestConroller implements ServletContextAware {
	
	private ServletContext context;
	
	
	@PostMapping("/upload")
	public ResponseEntity<Void> upload(@RequestParam("files") MultipartFile[] multipartFiles){
		try {
			System.out.println("Files liste");
			for (MultipartFile file : multipartFiles) {
				System.out.println("file Name : "+file.getOriginalFilename());
				System.out.println("file Size : "+file.getSize());
				System.out.println("file type : "+file.getContentType());
				System.out.println("------------------------------------");
				save(file);
			}
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
	}
	
	private String save(MultipartFile file) {
		try {
		String fileName = file.getOriginalFilename();
		
		byte[] bytes = file.getBytes();
		java.nio.file.Path path = Paths.get(this.context.getRealPath("/uploads/images/"+fileName));
		System.out.println(path+"1");
		System.out.println(this.context.getRealPath("/uploads/images/")+"2");
		java.nio.file.Files.write(path, bytes);
		return fileName;
		}catch (Exception e) {
			return null;
		}
		
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.context= servletContext;
		System.out.println(this.context+"///");
		
	}
	
	
	
	

}
