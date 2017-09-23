package edge.app.modules.notification;

import java.util.List;

import edge.app.modules.profileConnection.ProfileConnection;

public interface NotificationService {

	void addNotificationForAction(ProfileConnection connection);

	void addNotificationForRequest(ProfileConnection connection);

	List<Notification> loadUnreadNotifications(String userName);

}