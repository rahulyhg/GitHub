package edge.app.modules.notification;

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

import edge.app.modules.notification.Notification;
import edge.app.modules.notification.NotificationService;
import edge.app.modules.profile.ProfileDetails;
import edge.app.modules.profileConnection.ProfileConnectionService;
import edge.core.config.CoreConstants;
import edge.core.exception.AppException;
import edge.core.modules.common.EdgeResponse;

@Controller
public class NotificationController {

	private static final Logger logger = LoggerFactory.getLogger(NotificationController.class);
	
	@Autowired
	private NotificationService notificationService;
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(CoreConstants.DEFAULT_DATE_FORMAT);
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));        
    }
	
	@RequestMapping(value={"/secured/loadNotifications"})
	public EdgeResponse<List<Notification>> loadNotifications(Principal principal){
		
		String userName = principal.getName();
		List<Notification> notifications = notificationService.loadNotifications(userName);
		
		if(notifications == null || notifications.size() == 0){
			return EdgeResponse.createErrorResponse(null,"You have no notifications!", null, null);
		}else{
			return EdgeResponse.createDataResponse(notifications, ""); 		
		}
	}

	@RequestMapping(value={"/secured/markNotificationAsRead"})
	public EdgeResponse<String> markNotificationAsRead(Principal principal,
			@RequestBody Long notificationId){
		try{
			String userName = principal.getName();
			notificationService.markNotificationAsRead(userName, notificationId);
			
			return EdgeResponse.createDataResponse("", "");
		}catch (AppException ex) {
			return EdgeResponse.createExceptionResponse(ex);
		}
			
		
	}
	
}
