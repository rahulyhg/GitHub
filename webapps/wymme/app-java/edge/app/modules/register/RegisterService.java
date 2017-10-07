package edge.app.modules.register;

import edge.app.modules.profile.ProfileDetails;

public interface RegisterService {
	ProfileDetails register(ProfileDetails profileDetails) throws Exception;
}
