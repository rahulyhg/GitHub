package edge.app.modules.memberships;

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

import edge.core.config.CoreConstants;
import edge.core.exception.AppException;
import edge.core.modules.common.EdgeResponse;

@Controller
public class MembershipsController {

private static final Logger logger = LoggerFactory.getLogger(MembershipsController.class);
	
	@Autowired
	private MembershipsService membershipsService;

	@InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(CoreConstants.DEFAULT_DATE_FORMAT);
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));        
    }

	@RequestMapping(value={"/secured/saveMembership"})
	public EdgeResponse<Membership> addMembership(
			@RequestBody Membership membership, Principal principal			
			) throws Exception{	
		try{
			Membership addMembership = membershipsService.saveMembership(membership, principal.getName());
			return EdgeResponse.createDataResponse(addMembership, "Membership Added Successfully with Id : " + addMembership.getMembershipId());
			
		}catch(AppException ae){
			return EdgeResponse.createExceptionResponse(ae);
		}
	}
	
	@RequestMapping(value={"/secured/getAllMemberships"})
	public EdgeResponse<List<Membership>> getAllMemberships( 
			Principal principal			
			) throws Exception{	
		try{	
			List<Membership> allMemberships = membershipsService.getAllMemberships(principal.getName());
			return EdgeResponse.createDataResponse(allMemberships, "");
			
		}catch(AppException ae){
			return EdgeResponse.createExceptionResponse(ae);
		}
	}

	public MembershipsService getMembershipsService() {
		return membershipsService;
	}
	
	public void setMembershipsService(MembershipsService membershipsService) {
		this.membershipsService = membershipsService;
	}

}
