package edge.app.modules.lost;

import edge.app.modules.profile.ProfileDetails;
import edge.core.modules.common.EdgeResponse;

public interface LostService {
	EdgeResponse<ProfileDetails> lost(ProfileDetails profileDetails) throws Exception;
}
