package edge.app.modules.profileConnection;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PROFILE_CONNECTION")
public class ProfileConnection {

	@Id
	@Column(nullable = false, length = 50)
	private String connectionId;
	
	@Column(nullable = false, length = 50)
	private String profileFrom;
	
	@Column(nullable = false, length = 50)
	private String profileTo;
	
	@Column(nullable = false, updatable=false)
	private Date requestedOn;
	
	@Column(nullable = true)
	private Date actionedOn;
	
	@Column(nullable = false, length = 50)
	private String connectionStatus = ConnectionStatusEnum.Requested.name();

	public String getConnectionId() {
		return connectionId;
	}

	public void setConnectionId(String connectionId) {
		this.connectionId = connectionId;
	}

	public Date getRequestedOn() {
		return requestedOn;
	}

	public void setRequestedOn(Date requestedOn) {
		this.requestedOn = requestedOn;
	}

	public String getConnectionStatus() {
		return connectionStatus;
	}

	public void setConnectionStatus(ConnectionStatusEnum connectionStatus) {
		this.connectionStatus = connectionStatus.name();
	}

	public String getProfileFrom() {
		return profileFrom;
	}

	public void setProfileFrom(String profileFrom) {
		this.profileFrom = profileFrom;
	}

	public String getProfileTo() {
		return profileTo;
	}

	public void setProfileTo(String profileTo) {
		this.profileTo = profileTo;
	}

	public void setConnectionId(String profileFrom, String profileTo) {

		if(profileFrom.startsWith("F")){
			this.connectionId = profileFrom + "-" + profileTo;
		}else{
			this.connectionId = profileTo + "-" + profileFrom;
		}
	}

	public Date getActionedOn() {
		return actionedOn;
	}

	public void setActionedOn(Date actionedOn) {
		this.actionedOn = actionedOn;
	}
	
}
