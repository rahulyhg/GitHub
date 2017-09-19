package edge.app.modules.profileWallInfo;

public interface ProfileWallInfoService {

	String getRemovedProfiles(String profileId, Integer len);

	void undoRemoveFromWall(String profileId, String toAdd);

	void removeFromWall(String profileId, String toRemove);

	void addToReadProfiles(String profileFrom, String profileTo);

	String getReadProfiles(String profileId);

}