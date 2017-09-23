package edge.app.modules.notification;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edge.app.modules.profileConnection.ProfileConnection;
import edge.core.modules.common.CommonHibernateDao;

@Component
public class NotificationServiceImpl implements NotificationService {

	private static final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);
	
	@Autowired
	private CommonHibernateDao commonHibernateDao;

	@Override
	public void addNotificationForAction(ProfileConnection connection) {
		Notification notification = new Notification();
		notification.setCategory(connection.getConnectionStatus().getCategory());
		notification.setNotificationStatus(NotificationStatusEnum.Unread);
		notification.setProfileId(connection.getProfileFrom());
		notification.setProfileOther(connection.getProfileTo());
		notification.setNotificationText(connection.getText());
		
		commonHibernateDao.save(notification);
	}
	
	@Override
	public void addNotificationForRequest(ProfileConnection connection) {
		Notification notification = new Notification();
		notification.setCategory(connection.getConnectionStatus().getCategory());
		notification.setNotificationStatus(NotificationStatusEnum.Unread);
		notification.setProfileId(connection.getProfileTo());
		notification.setProfileOther(connection.getProfileFrom());
		notification.setNotificationText(connection.getText());
		
		commonHibernateDao.save(notification);
	}

	@Override
	public List<Notification> loadUnreadNotifications(String userName) {
		return commonHibernateDao.getHibernateTemplate().find(" from Notification where notificationStatus = '" + NotificationStatusEnum.Unread.name() + "'");
	}
	

}
