package edge.app.modules.profileWallInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import edge.core.modules.common.CommonHibernateDao;

@Component
public class ProfileWallInfoServiceImpl implements ProfileWallInfoService {
	
	@Autowired
	private CommonHibernateDao commonHibernateDao;
	
	@Override
	public String getRemovedProfiles(String profileId, Integer len) {
		
		ProfileWallInfo profileWallInfo = commonHibernateDao.getEntityById(ProfileWallInfo.class, profileId);
		if(profileWallInfo == null || profileWallInfo.getRemovedProfiles() == null){
			return "''";
		}else{
			String removedProfiles = profileWallInfo.getRemovedProfiles();
			if(removedProfiles == null || removedProfiles.trim().length() == 0){
				removedProfiles = "''";
			}
			if(len != null && removedProfiles.length() > len){
				removedProfiles = removedProfiles.substring(0, len);
			}
			return removedProfiles;
		}
	}
	
	@Override
	@Transactional
	public void undoRemoveFromWall(String profileId, String toAdd) {
		
		ProfileWallInfo profileWallInfo = commonHibernateDao.getEntityById(ProfileWallInfo.class, profileId);
		if(profileWallInfo == null){
			// HOW? TODO
		}else{
			String removedProfiles = profileWallInfo.getRemovedProfiles();
			if(removedProfiles.contains(toAdd)){
				String val = "'" + toAdd +"',";
				removedProfiles = removedProfiles.replace(val, "");
				
				val = ",'" + toAdd +"'";
				removedProfiles = removedProfiles.replace(val, "");

				val = "'" + toAdd +"'";
				removedProfiles = removedProfiles.replace(val, "");
			}
			profileWallInfo.setRemovedProfiles(removedProfiles);
		}
		commonHibernateDao.saveOrUpdate(profileWallInfo);
	}
	
	@Override
	@Transactional
	public void removeFromWall(String profileId, String toRemove) {
		
		ProfileWallInfo profileWallInfo = commonHibernateDao.getEntityById(ProfileWallInfo.class, profileId);
		if(profileWallInfo == null){
			profileWallInfo = new ProfileWallInfo();
			profileWallInfo.setProfileId(profileId);
			profileWallInfo.setRemovedProfiles("'" + toRemove +"'");
		}else{
			String removedProfiles = profileWallInfo.getRemovedProfiles();
			if(removedProfiles == null || removedProfiles.trim().length() == 0){
				removedProfiles = "'" + toRemove + "'";
			}else if(!removedProfiles.contains(toRemove)){
				removedProfiles = "'" + toRemove +"'," + removedProfiles;
			}
			profileWallInfo.setRemovedProfiles(removedProfiles);
		}
		commonHibernateDao.saveOrUpdate(profileWallInfo);
	}


	@Override
	public String getReadProfiles(String profileId) {
		ProfileWallInfo profileWallInfo = commonHibernateDao.getEntityById(ProfileWallInfo.class, profileId);
		if(profileWallInfo == null || profileWallInfo.getReadProfiles() == null){
			return "''";
		}else{
			String readProfiles = profileWallInfo.getReadProfiles();
			if(readProfiles == null || readProfiles.trim().length() == 0){
				readProfiles = "''";
			}
			return readProfiles;
		}
	}
	
	@Override
	@Transactional
	public void addToReadProfiles(String profileFrom, String profileTo) {

		ProfileWallInfo profileWallInfo = commonHibernateDao.getEntityById(ProfileWallInfo.class, profileFrom);
		if(profileWallInfo == null){
			profileWallInfo = new ProfileWallInfo();
			profileWallInfo.setProfileId(profileFrom);
			profileWallInfo.setReadProfiles("'" + profileTo +"'");
		}else{
			String readProfiles = profileWallInfo.getReadProfiles();
			if(readProfiles == null || readProfiles.trim().length() == 0){
				readProfiles = "'" + profileTo +"'";
			}else if(!readProfiles.contains(profileTo)){
				readProfiles = "'" + profileTo +"'," + readProfiles;
			}
			profileWallInfo.setReadProfiles(readProfiles);
		}
		commonHibernateDao.saveOrUpdate(profileWallInfo);
		
	}
}
