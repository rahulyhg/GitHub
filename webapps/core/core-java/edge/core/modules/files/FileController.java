package edge.core.modules.files;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import edge.core.modules.common.EdgeResponse;

@Controller
public class FileController {
	
	@Autowired
	private FileService fileService;

	@RequestMapping(value={"/secured/uploadFile"})
	public EdgeResponse<String> uploadProfilePic(
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

}
