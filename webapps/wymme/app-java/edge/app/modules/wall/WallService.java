package edge.app.modules.wall;

import java.util.List;

import edge.app.modules.profile.ProfileDetails;
import edge.core.modules.common.EdgeResponse;

public interface WallService {

	public EdgeResponse<List<ProfileDetails>> loadWallProfiles(String userName);

}