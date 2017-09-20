package edge.app.modules.profileConnection;

import java.util.List;

import edge.app.modules.profile.ProfileDetails;
import edge.app.modules.search.ConnectionAction;

public interface ProfileConnectionService {

	void sendConnectionRequest(String userName, String profileTo);

	List<ProfileDetails> searchProfiles(String userName, String searchType);

	void actionRequest(String userName, ConnectionAction connectionAction);

}
