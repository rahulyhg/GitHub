package edge.core.modules.files;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;


public interface FileService {

	void uploadFile(String entityName, String idColumn, String storageColumn, String entityId, MultipartFile file, String loggedInId) throws Exception;

	File getFile(String entityName, String idColumn, String storageColumn, String entityId, String fileName, String loggedInId);

	void compressAndUploadImage(String entityName, String idColumn, String storageColumn, String entityId, MultipartFile file, int width, String loggedInId) throws Exception;

}