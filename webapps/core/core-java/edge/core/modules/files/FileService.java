package edge.core.modules.files;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

	void uploadFile(Integer entityId, String entityName, String columnName, MultipartFile file, String loggedInId) throws IOException, Exception;
	
	File getFile(String columnName, String fileName, Integer entityId, String loggedInId);
}