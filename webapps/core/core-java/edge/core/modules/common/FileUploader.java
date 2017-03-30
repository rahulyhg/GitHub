package edge.core.modules.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUploader{
	
	private static FileUploader INSTANCE = null;
	
	@Value(value = "${property.baseDirectory}")
	private String baseDirectory;
	
	public FileUploader() {
		INSTANCE = this;
	}

	public String getBaseDirectory() {
		return baseDirectory;
	}

	public void setBaseDirectory(String baseDirectory) {
		this.baseDirectory = baseDirectory;
	}
	
	private static String getFileExtension(MultipartFile file) {
	    String name = file.getOriginalFilename();
	    try {
	        return name.substring(name.lastIndexOf(".") + 1);
	    } catch (Exception e) {
	        return "";
	    }
	}

	public static String uploadFile(int systemId, int entityId, String uploadType, MultipartFile file) throws IOException {
		String filePath = INSTANCE.baseDirectory + File.separatorChar + uploadType + File.separatorChar + entityId + "." + getFileExtension(file);
		Path path = Paths.get(filePath);
		Path parentDir = path.getParent();
		if (!Files.exists(parentDir)){
		    Files.createDirectories(parentDir);
		}
		Files.write(path, file.getBytes());
		return filePath;
	}
}
