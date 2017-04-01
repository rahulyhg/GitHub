package edge.app.modules.employees;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import edge.core.config.CoreConstants;
import edge.core.exception.AppException;
import edge.core.modules.common.EdgeResponse;

@Controller
public class EmployeesController {

private static final Logger logger = LoggerFactory.getLogger(EmployeesController.class);
	
	@Autowired
	private EmployeesService employeesService;

	@InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(CoreConstants.DEFAULT_DATE_FORMAT);
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));        
    }
	
	public EmployeesService getEmployeesService() {
		return employeesService;
	}

	@RequestMapping(value={"/secured/saveEmployee"})
	public EdgeResponse<Employee> addEmployee(
			@RequestBody Employee employee, Principal principal			
			) throws Exception{	
		try{
			Employee addEmployee = employeesService.saveEmployee(employee, principal.getName());
			return EdgeResponse.createDataResponse(addEmployee, "Employee added Successfully with ID : " + addEmployee.getEmployeeId());
			
		}catch(AppException ae){
			return EdgeResponse.createExceptionResponse(ae);
		}
		
	}

	@RequestMapping(value={"/secured/getActiveEmployees"})
	public EdgeResponse<List<Employee>> getActiveEmployees( 
			Principal principal			
			) throws Exception{	
		try{
			List<Employee> allEmployees = employeesService.getActiveEmployees(principal.getName());
			return EdgeResponse.createDataResponse(allEmployees, "");
		}catch(AppException ae){
			return EdgeResponse.createExceptionResponse(ae);
		}
	}
	
	@RequestMapping(value={"/secured/getAllEmployees"})
	public EdgeResponse<List<Employee>> getAllEmployees( 
			Principal principal			
			) throws Exception{	
		try{
			List<Employee> allEmployees = employeesService.getAllEmployees(principal.getName());
			return EdgeResponse.createDataResponse(allEmployees, "");
		}catch(AppException ae){
			return EdgeResponse.createExceptionResponse(ae);
		}
	}
	
	public void setEmployeesService(EmployeesService employeesService) {
		this.employeesService = employeesService;
	}

}
