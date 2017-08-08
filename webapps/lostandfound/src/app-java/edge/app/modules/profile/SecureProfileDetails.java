package edge.app.modules.profile;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PROFILE_SECURE")
public class SecureProfileDetails {

	@Id
	private String profileId;
	
	@Column(nullable = false, length = 100)	
	private String name;
	
	@Column(nullable = false, length = 100)
	private String cell;
	
	@Column(nullable = false, length = 100)
	private String cellParents;
	
	@Column(nullable = false, length = 100)
	private String email;
	
	private String facebook;
	private String twitter;
	private String linkedIn;
	
	
	public String getCell() {
		return cell;
	}
	public void setCell(String cell) {
		this.cell = cell;
	}
	public String getCellParents() {
		return cellParents;
	}
	public void setCellParents(String cellParents) {
		this.cellParents = cellParents;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFacebook() {
		return facebook;
	}
	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}
	public String getTwitter() {
		return twitter;
	}
	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}
	public String getLinkedIn() {
		return linkedIn;
	}
	public void setLinkedIn(String linkedIn) {
		this.linkedIn = linkedIn;
	}
	public String getProfileId() {
		return profileId;
	}
	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
