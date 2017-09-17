package edge.app.modules.wall;

import java.util.List;

import edge.app.modules.profile.ProfileDetails;
import edge.core.modules.common.EdgeResponse;

public interface WallService {

	public List<ProfileDetails> loadWallProfiles(String userName);

	public void removeFromWall(String userName, String toRemove);

}