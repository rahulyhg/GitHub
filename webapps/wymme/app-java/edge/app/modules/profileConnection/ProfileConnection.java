package edge.app.modules.profileConnection;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import edge.core.utils.CoreDateUtils;

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

	public ConnectionStatusEnum getConnectionStatus() {
		return ConnectionStatusEnum.valueOf(connectionStatus);
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

	public void setConnectionId(String profile1, String profile2) {

		String connectionId = ProfileConnection.getConnectionId(profile1, profile2);
		this.connectionId = connectionId;
		
	}

	public static String getConnectionId(String profile1, String profile2) {
		if(profile1.startsWith("F")){
			return profile1 + "-" + profile2;
		}else{
			return profile2 + "-" + profile1;
		}
	}

	public Date getActionedOn() {
		return actionedOn;
	}

	public void setActionedOn(Date actionedOn) {
		this.actionedOn = actionedOn;
	}

	public String getText() {
		String retValue="";
		switch(getConnectionStatus()){
		case Requested: 
			retValue = " Congratulations! You have received a connection request from Profile '" + profileFrom + "' on " + CoreDateUtils.dateToStandardSting(requestedOn) + ".";
			break;
		case Accepted:
			retValue = " Hurray, Your connection request has been 'Accepted' by Profile '" + profileTo + "' on " + CoreDateUtils.dateToStandardSting(actionedOn) + ". Good Luck!";
			break;
		case Rejected:
			retValue = " Ooh, Your connection request has been 'Rejected' by Profile '" + profileTo + "' on " + CoreDateUtils.dateToStandardSting(actionedOn) + ". Never Mind, We would help you get better match!";
			break;
		}
		return retValue;
	}
	
}
