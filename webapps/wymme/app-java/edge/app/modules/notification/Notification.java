package edge.app.modules.notification;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "NOTIFICATION")
public class Notification {

	@Id	
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long notificationId;

	@Column(nullable = false, length = 50)
	private String profileId;

	@Column(nullable = true, length = 50)
	private String profileOther;

	@Column(nullable = false, length = 50)
	private String category;
		
	@Column(nullable = false, length = 500)
	private String notificationText;
	
	@Column(nullable = false, length = 20)
	private String notificationStatus = NotificationStatusEnum.Unread.name();

	public Long getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(Long notificationId) {
		this.notificationId = notificationId;
	}

	public String getProfileId() {
		return profileId;
	}

	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}

	public String getNotificationText() {
		return notificationText;
	}

	public void setNotificationText(String notificationText) {
		this.notificationText = notificationText;
	}

	public NotificationStatusEnum getNotificationStatus() {
		return NotificationStatusEnum.valueOf(notificationStatus);
	}

	public void setNotificationStatus(NotificationStatusEnum notificationStatus) {
		this.notificationStatus = notificationStatus.name();
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getProfileOther() {
		return profileOther;
	}

	public void setProfileOther(String profileOther) {
		this.profileOther = profileOther;
	}
	
}
