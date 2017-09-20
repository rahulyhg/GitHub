package edge.app.modules.search;

import edge.app.modules.profileConnection.ConnectionStatusEnum;

public class ConnectionAction {
	
	private String profileId;

	private String connectionStatus;

	public String getProfileId() {
		return profileId;
	}

	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}

	public ConnectionStatusEnum getConnectionStatus() {
		return ConnectionStatusEnum.valueOf(connectionStatus);
	}

	public void setConnectionStatus(ConnectionStatusEnum connectionStatus) {
		this.connectionStatus = connectionStatus.name();
	}
}
