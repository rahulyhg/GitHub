package edge.core.modules.files;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import edge.core.config.CoreConstants;
import edge.core.exception.AppException;
import edge.core.modules.auth.SecurityRoles;
import edge.core.modules.common.CommonHibernateDao;
import edge.core.modules.parents.ParentsService;

@Component
public class FileServiceImpl implements FileService{
	
	@Value(value = "${property.baseDirectory}")
	private String baseDirectory;
	
	@Value(value = "${property.appName}")
	private String appName;
	
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

	private int updateEntity(String entityName, String idColumn, String storageColumn, String entityId, String fileName, Integer parentId) throws Exception {
		
		String query = "";
		
		if(parentsService.isParentServiceEnabled()){
			query = " update " + entityName + " set " + storageColumn + " = '" + fileName + "'"
					+ " where parentId = '" + parentId + "'"
					+ " and " + idColumn + " = '" + entityId + "'"; 
		}else{
			query = " update " + entityName + " set " + storageColumn + " = '" + fileName + "'"
					+ " where " + idColumn + " = '" + entityId+ "'"; 
			
		}
		
		return commonHibernateDao.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(query).executeUpdate();
	}

	@Override
	@Transactional
	public void uploadFile(String entityName, String idColumn, String storageColumn, String entityId, MultipartFile file, String loggedInId) throws Exception {
		int parentId = 0;
		if(parentsService.isParentServiceEnabled()){
			parentId = parentsService.getParentId(loggedInId, SecurityRoles.PARENT_OPERATOR);
		}
		String fileName = RandomStringUtils.randomAlphanumeric(CoreConstants.PROFILE_ID_SIZE).toUpperCase() + "." + getFileExtension(file); 
		
		String filePath = getFilePath(parentId, entityId, storageColumn, fileName);
		
		Path path = Paths.get(filePath);
		Path parentDir = path.getParent();
		FileUtils.deleteDirectory(new File(getBasePath(parentId, entityId, storageColumn)));
		Files.createDirectories(parentDir);
		Files.write(path, file.getBytes());
		updateEntity(entityName, idColumn, storageColumn, entityId, fileName, parentId);
	}

	@Override
	@Transactional
	public void compressAndUploadImage(String entityName, String idColumn, String storageColumn, String entityId, MultipartFile file, int width, String loggedInId) throws Exception {
		int parentId = 0;
		if(parentsService.isParentServiceEnabled()){
			parentId = parentsService.getParentId(loggedInId, SecurityRoles.PARENT_OPERATOR);
		}
		String fileName = RandomStringUtils.randomAlphanumeric(CoreConstants.PROFILE_ID_SIZE).toUpperCase() + "." + getFileExtension(file); 
		
		String filePath = getFilePath(parentId, entityId, storageColumn, fileName);
		
		Path path = Paths.get(filePath);
		Path parentDir = path.getParent();
		FileUtils.deleteDirectory(new File(getBasePath(parentId, entityId, storageColumn)));
		Files.createDirectories(parentDir);
		
		// Retrieve image from the classpath.
        InputStream is = file.getInputStream(); 

        // Prepare buffered image.
        BufferedImage image = ImageIO.read(is);
        
		ImageIO.write(Scalr.resize(image, Method.ULTRA_QUALITY, width, Scalr.OP_BRIGHTER), "JPG", new File(filePath));

		//Files.write(path, file.getBytes());
		updateEntity(entityName, idColumn, storageColumn, entityId, fileName, parentId);
		
		addWatermark(new File(filePath));
	}
	
	@Override
	@Transactional
	public File getFile(String entityName, String idColumn, String storageColumn, String entityId, String fileName, String loggedInId) {
		
		String query = "";
		int parentId = 0;
		
		if(parentsService.isParentServiceEnabled()){
			parentId = parentsService.getParentId(loggedInId, SecurityRoles.PARENT_OPERATOR);
			
			query= " select " + storageColumn + " from "+ entityName 
					+ " where parentId = '" + parentId + "'"
					+ " and " + idColumn + " = '" + entityId + "'"; 
		} else{
			query= " select " + storageColumn + " from "+ entityName 
					+ " where " + idColumn + " = '" + entityId + "'"; 
		}
		
		List list = commonHibernateDao.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(query).list();

		if(list != null && list.size() == 1){
			fileName = (String) list.get(0);
		}else{
			throw new AppException(null, "No such file exists!");
		}
		
		return new File(getFilePath(parentId, entityId, storageColumn, fileName));
	}

	public String getFilePath(int parentId, String entityId, String storageColumn,  String fileName) {
		if(fileName == null || fileName.equals("NA") || fileName.trim().length() == 0){
			return baseDirectory + File.separatorChar + "NA.jpg" ;
		}
		return getBasePath(parentId, entityId, storageColumn) + File.separatorChar + fileName;
	}
	
	public String getBasePath(int parentId, String entityId, String storageColumn) {
		return baseDirectory + File.separatorChar + parentId + File.separatorChar + entityId + File.separatorChar + storageColumn;
	}
	
	private void addWatermark(File imageFile) {
        try {
        	BufferedImage image = ImageIO.read(imageFile);
        	
        	Graphics graphics = image.getGraphics();
        	graphics.setFont(new Font("candara", Font.PLAIN, 20));
        	String watermark = "\u00a9 " + appName;
            // File watermarkFile = new File(getClass().getResource("/watermark.png").toURI());
            // BufferedImage watermark = ImageIO.read(watermarkFile);
            
            graphics.drawString(watermark, image.getWidth()-180, image.getHeight()-20);
            ImageIO.write(image, "jpg", imageFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


