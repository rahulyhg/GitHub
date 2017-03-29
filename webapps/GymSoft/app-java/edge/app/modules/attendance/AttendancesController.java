package edge.app.modules.attendance;

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
public class AttendancesController {

private static final Logger logger = LoggerFactory.getLogger(AttendancesController.class);
	
	@Autowired
	private AttendancesService attendancesService;

	@InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(CoreConstants.DEFAULT_DATE_FORMAT);
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));        
    }
	
	public AttendancesService getAttendancesService() {
		return attendancesService;
	}

	@RequestMapping(value={"/secured/saveAttendance"})
	public EdgeResponse<Attendance> addAttendance(
			@RequestBody Attendance attendance, Principal principal			
			) throws Exception{	
		try{	
			Attendance addAttendance = attendancesService.saveAttendance(attendance, principal.getName());
			return EdgeResponse.createDataResponse(addAttendance, "Attendance saved Successfully with ID : " + addAttendance.getAttendanceId());
		}catch(AppException ae){
			return EdgeResponse.createExceptionResponse(ae);
		}
	}
	
	@RequestMapping(value={"/secured/getAllAttendances"})
	public EdgeResponse<List<Attendance>> getAllAttendances( 
			Principal principal			
			) throws Exception{	
		try{	
			List<Attendance> allAttendances = attendancesService.getAllAttendances(principal.getName());
			return EdgeResponse.createDataResponse(allAttendances, "");
			
		}catch(AppException ae){
			return EdgeResponse.createExceptionResponse(ae);
		}
	}
	
	public void setAttendancesService(AttendancesService attendancesService) {
		this.attendancesService = attendancesService;
	}

}
