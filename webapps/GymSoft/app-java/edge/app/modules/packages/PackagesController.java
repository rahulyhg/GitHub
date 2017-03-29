package edge.app.modules.packages;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import edge.app.modules.gyms.GymsService;
import edge.appCore.modules.auth.SecurityRoles;
import edge.core.config.CoreConstants;
import edge.core.exception.AppException;
import edge.core.modules.common.EdgeResponse;

@Controller
public class PackagesController {

private static final Logger logger = LoggerFactory.getLogger(PackagesController.class);
	
	@Autowired
	private PackagesService packagesService;
	
	@Autowired
	private GymsService gymsService;

	@InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(CoreConstants.DEFAULT_DATE_FORMAT);
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));        
    }
	
	public PackagesService getPackagesService() {
		return packagesService;
	}

	@RequestMapping(value={"/secured/savePackage"})
	public EdgeResponse<Package> addPackage(
			@RequestBody Package packagei, Principal principal			
			) throws Exception{	
		try{
			Package addPackage = packagesService.savePackage(packagei, principal.getName());
			return EdgeResponse.createDataResponse(addPackage, "Package added Successfully with ID : " + addPackage.getPackageId());
		}catch(AppException ae){
			return EdgeResponse.createExceptionResponse(ae);
		}
	}
	
	@RequestMapping(value={"/secured/getActivePackages"})
	public EdgeResponse<List<Package>> getActivePackages( 
			Principal principal			
			) throws Exception{	
		
		try{	
			List<Package> allPackages = packagesService.getActivePackages(principal.getName());
			return EdgeResponse.createDataResponse(allPackages, "");
				
		}catch(AppException ae){
			return EdgeResponse.createExceptionResponse(ae);
		}
	}
	
	@RequestMapping(value={"/secured/getAllPackages"})
	public EdgeResponse<List<Package>> getAllPackages( 
			Principal principal			
			) throws Exception{	
		
		try{	
			List<Package> allPackages = packagesService.getAllPackages(principal.getName());
			return EdgeResponse.createDataResponse(allPackages, "");
				
		}catch(AppException ae){
			return EdgeResponse.createExceptionResponse(ae);
		}
	}
	
	public void setPackagesService(PackagesService packagesService) {
		this.packagesService = packagesService;
	}

	public GymsService getGymsService() {
		return gymsService;
	}

	public void setGymsService(GymsService gymsService) {
		this.gymsService = gymsService;
	}
}
