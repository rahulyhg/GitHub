package edge.core.modules.files;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import edge.core.modules.auth.SecurityRoles;
import edge.core.modules.common.CommonHibernateDao;
import edge.core.modules.parents.ParentsService;

@Component
public class FileServiceImpl implements FileService{
	
	@Value(value = "${property.baseDirectory}")
	private String baseDirectory;
	
	@Autowired
	private ParentsService parentsService;
	
	@Autowired
	private CommonHibernateDao commonHibernateDao;
	
	public FileServiceImpl() {

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

	private int updateEntity(Integer entityId, String entityName, String columnName, int parentId, String fileName) throws Exception {
		String query= " update " + entityName + " set " + columnName + " = '" + fileName + "'"
					+ " where parentId = " + parentId 
					+ " and " + entityName.toLowerCase() + "Id = " + entityId; 
		
		return commonHibernateDao.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(query).executeUpdate();
	}

	@Override
	@Transactional
	public void uploadFile(Integer entityId, String entityName, String columnName, MultipartFile file, String loggedInId) throws Exception {
		int parentId = parentsService.getParentId(loggedInId, SecurityRoles.PARENT_OPERATOR);
		String filePath = baseDirectory + File.separatorChar + parentId + File.separatorChar + columnName + File.separatorChar + entityId + "." + getFileExtension(file);
		Path path = Paths.get(filePath);
		Path parentDir = path.getParent();
		if (!Files.exists(parentDir)){
		    Files.createDirectories(parentDir);
		}
		Files.write(path, file.getBytes());
		updateEntity(entityId, entityName, columnName, parentId, entityId + "." + getFileExtension(file));
	}
}
