package edge.app.modules.register;

import edge.app.modules.profile.ProfileDetails;
import edge.core.modules.common.EdgeResponse;

public interface RegisterService {
	EdgeResponse<ProfileDetails> register(ProfileDetails profileDetails) throws Exception;
}
