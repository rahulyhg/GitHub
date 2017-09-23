package edge.app.modules.notification;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;

import edge.app.modules.common.AppConstants;
import edge.app.modules.profileConnection.ProfileConnection;
import edge.core.exception.AppException;
import edge.core.modules.auth.SignUpEntity;
import edge.core.modules.common.CommonHibernateDao;

@Component
public class NotificationServiceImpl implements NotificationService {

	private static final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);
	
	@Autowired
	private CommonHibernateDao commonHibernateDao;

	@Override
	public void addNotificationForAction(ProfileConnection connection) {
		Notification notification = new Notification();
		notification.setConnectionStatus(connection.getConnectionStatus());
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
		notification.setConnectionStatus(connection.getConnectionStatus());
		notification.setCategory(connection.getConnectionStatus().getCategory());
		notification.setNotificationStatus(NotificationStatusEnum.Unread);
		notification.setProfileId(connection.getProfileTo());
		notification.setProfileOther(connection.getProfileFrom());
		notification.setNotificationText(connection.getText());
		
		commonHibernateDao.save(notification);
	}

	@Override
	public List<Notification> loadUnreadNotifications(String userName) {
		
		SignUpEntity signUpEntity = commonHibernateDao.getEntityById(SignUpEntity.class, userName);
		String profileId = signUpEntity.getProfileId();
		
		return commonHibernateDao.getHibernateTemplate().find(" from Notification where profileId = '" + profileId + "' and notificationStatus = '" + NotificationStatusEnum.Unread.name() + "'");
	}

	@Override
	public List<Notification> loadNotifications(String userName) {
		
		SignUpEntity signUpEntity = commonHibernateDao.getEntityById(SignUpEntity.class, userName);
		String profileId = signUpEntity.getProfileId();
		
		HibernateTemplate hibernateTemplate = commonHibernateDao.getHibernateTemplate();
		hibernateTemplate.setMaxResults(AppConstants.MAX_NOTIFICATIONS_SIZE);
		
		return hibernateTemplate.find(" from Notification where profileId = '" + profileId + "' order by notificationStatus desc, raisedOn desc ");
	
	}

	@Override
	public void markNotificationAsRead(String userName, Long notificationId) {
		SignUpEntity signUpEntity = commonHibernateDao.getEntityById(SignUpEntity.class, userName);
		String profileId = signUpEntity.getProfileId();
		
		Notification notification = commonHibernateDao.getEntityById(Notification.class, notificationId);
		if(notification.getProfileId().equals(profileId) && notification.getNotificationStatus() != NotificationStatusEnum.Read){
			notification.setActionedOn(new Date());
			notification.setNotificationStatus(NotificationStatusEnum.Read);
			commonHibernateDao.update(notification);
		}else{
			throw new AppException(null, "There is no such notification.");
		}
		
	}
	

}
