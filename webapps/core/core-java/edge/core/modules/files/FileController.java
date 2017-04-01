package edge.core.modules.files;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import edge.core.modules.common.EdgeResponse;

@Controller
public class FileController {
	
	@Autowired
	private FileService fileService;

	@RequestMapping(value={"/secured/uploadFile"})
	public EdgeResponse<String> uploadFile(
			@RequestParam("entityId") String entityId,
			@RequestParam("entityName") String entityName,
			@RequestParam("columnName") String columnName,
			@RequestParam("file") MultipartFile file,
			Principal principal) {
	
		try {
			fileService.uploadFile(Integer.valueOf(entityId),entityName,columnName, file, principal.getName());
			
		} catch (Exception e) {
			e.printStackTrace();
			return EdgeResponse.createErrorResponse(null, "Error while uploading..", e.getMessage(), null);
		}
		return EdgeResponse.createDataResponse("Success", " File uploaded Succcesfully " );
	}
	
	@RequestMapping(value = "/secured/files/{entityName}/{columnName}/{entityId}", method = RequestMethod.GET)
	@ResponseBody
	public FileSystemResource getFile(
			@PathVariable("entityName") String entityName,
			@PathVariable("columnName") String columnName,
			@PathVariable("entityId") String entityId,
			Principal principal) {
	    return new FileSystemResource(fileService.getFile(entityName, columnName, Integer.valueOf(entityId), principal.getName())); 
	}

}