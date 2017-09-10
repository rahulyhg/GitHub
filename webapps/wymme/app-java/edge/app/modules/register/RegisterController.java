package edge.app.modules.register;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import edge.app.modules.profile.ProfileDetails;
import edge.core.config.CoreConstants;
import edge.core.modules.common.EdgeResponse;

@Controller
public class RegisterController {

	private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(CoreConstants.DEFAULT_DATE_FORMAT);
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));        
    }
	
	@Autowired
	private RegisterServiceImpl registerService;
	
	@RequestMapping(value={"/unsecured/register"})
	public EdgeResponse<ProfileDetails> register(
			@RequestBody ProfileDetails profileDetails 			
			) throws Exception{			
		return registerService.register(profileDetails);
		
	}

	public RegisterServiceImpl getRegisterService() {
		return registerService;
	}

	public void setRegisterService(RegisterServiceImpl registerService) {
		this.registerService = registerService;
	}
}
