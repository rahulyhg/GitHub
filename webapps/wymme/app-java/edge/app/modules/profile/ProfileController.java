package edge.app.modules.profile;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import edge.core.config.CoreConstants;
import edge.core.exception.AppException;
import edge.core.modules.auth.AuthService;
import edge.core.modules.auth.SignUpEntity;
import edge.core.modules.common.EdgeResponse;
import edge.core.modules.files.FileService;

@Controller
public class ProfileController {

	private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);
	
	@Autowired
	private ProfileService profileService;

	@Autowired
	private AuthService authService;
	
	@Autowired
	private FileService fileService;
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(CoreConstants.DEFAULT_DATE_FORMAT);
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));        
    }
	
	@RequestMapping(value="/secured/uploadImage", method=RequestMethod.POST, headers = ("content-type=multipart/form-data"))
	public EdgeResponse<String> uploadImage(
			MultipartHttpServletRequest request,
			Principal principal) throws Exception {
	
		try {
		    MultipartFile file = request.getFile("file");
		    String imageType = (String) request.getParameter("imageType");
		    String emailId = principal.getName();
		    SignUpEntity signUpEntity = authService.getSignUpEntity(emailId);
			fileService.uploadFile("ProfileDetails", "profileId", imageType, signUpEntity.getProfileId(), file, principal.getName());
			
			return EdgeResponse.createDataResponse("", " Image Uploaded Successfully. ");
		}catch (AppException ex) {
			return EdgeResponse.createExceptionResponse(ex);
		}
	}

	@RequestMapping(value = "/secured/getImage/{storageColumn}/{entityId}/{fileName}", method = RequestMethod.GET)
	@ResponseBody
	public FileSystemResource getImage(
			@PathVariable("storageColumn") String storageColumn,
			@PathVariable("entityId") String entityId,
			@PathVariable("fileName") String fileName,
			Principal principal) {
	    return new FileSystemResource(fileService.getFile("ProfileDetails", "profileId", storageColumn, entityId, fileName, principal.getName()));
	}
	
	@RequestMapping(value={"/secured/profile/openMyProfile"})
	public EdgeResponse<ProfileDetails> openMyProfile(
			Principal principal
			){			
		ProfileDetails profileDetails = profileService.getFullProfileDetails(principal.getName());		
		return EdgeResponse.createDataResponse(profileDetails, null);
	}
	
	@RequestMapping(value={"/secured/profile/updateMyProfile"})
	public EdgeResponse<ProfileDetails> updateMyProfile(
			Principal principal, @RequestBody ProfileDetails profileDetails
			){	
		try {
			profileDetails = profileService.getFullProfileDetails(principal.getName());
			return EdgeResponse.createDataResponse(profileDetails, "Your profile has heen Successfully Updated!");	
		}catch (AppException ex) {
			return EdgeResponse.createExceptionResponse(ex);
		}
		
	}
}
