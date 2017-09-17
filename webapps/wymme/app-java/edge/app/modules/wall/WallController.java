package edge.app.modules.wall;

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
import org.springframework.web.bind.annotation.RequestMapping;

import edge.app.modules.profile.ProfileDetails;
import edge.core.config.CoreConstants;
import edge.core.modules.common.EdgeResponse;

@Controller
public class WallController {

	private static final Logger logger = LoggerFactory.getLogger(WallController.class);
	
	@Autowired
	private WallService wallService;
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(CoreConstants.DEFAULT_DATE_FORMAT);
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));        
    }
	
	@RequestMapping(value={"/secured/loadWallProfiles"})
	public EdgeResponse<List<ProfileDetails>> loadWallProfiles(Principal principal){
		String userName = principal.getName();
		return wallService.loadWallProfiles(userName);
	}
	
}
