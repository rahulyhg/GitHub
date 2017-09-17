package edge.app.modules.wall;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PROFILE_WALL_INFO")
public class ProfileWallInfo {

	@Id	
	@Column(nullable = false, length = 50)
	private String profileId;

	@Column(name = "REMOVED", length = 65535)
	private String removedProfiles;
	
	public String getProfileId() {
		return profileId;
	}

	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}

	public String getRemovedProfiles() {
		return removedProfiles;
	}

	public void setRemovedProfiles(String removedProfiles) {
		this.removedProfiles = removedProfiles;
	}
	

}
