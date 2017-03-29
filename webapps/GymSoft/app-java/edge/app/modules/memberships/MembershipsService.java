package edge.app.modules.memberships;

import java.util.List;

public interface MembershipsService {

	Membership saveMembership(Membership membership, String loggedInId);

	List<Membership> getAllMemberships(String loggedInId);

}
