package edge.app.modules.profile;

public interface ProfileService {

	ProfileDetails getFullProfileDetails(String emailId);

	ProfileDetails updateMyProfile(ProfileDetails profileDetails, String userName);

}
