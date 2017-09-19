package edge.app.modules.profileWallInfo;

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

	@Column(name = "REMOVED_PROFILES", length = 65535)
	private String removedProfiles = "";

	@Column(name = "READ_PROFILES", length = 65535)
	private String readProfiles = "";
	
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

	public String getReadProfiles() {
		return readProfiles;
	}

	public void setReadProfiles(String readProfiles) {
		this.readProfiles = readProfiles;
	}

}
