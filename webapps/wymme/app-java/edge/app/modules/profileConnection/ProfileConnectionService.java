package edge.app.modules.profileConnection;

import java.util.List;

import edge.app.modules.profile.ProfileDetails;

public interface ProfileConnectionService {

	void sendConnectionRequest(String userName, String profileTo);

	List<ProfileDetails> searchProfiles(String userName, String searchType);

}
