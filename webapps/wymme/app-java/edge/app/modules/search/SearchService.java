package edge.app.modules.search;

import java.util.List;

import edge.app.modules.profile.ProfileDetails;

public interface SearchService {

	public abstract ProfileDetails searchById(String profileId);
	
	public List<ProfileDetails> loadRemovedProfiles(String userName);
	
	public void undoRemoveFromWall(String userName, String toAdd);

}