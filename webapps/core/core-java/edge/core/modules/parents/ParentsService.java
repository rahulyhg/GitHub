package edge.core.modules.parents;

import java.util.List;

import edge.core.modules.auth.SecurityRoles;

public interface ParentsService {

	Parent addParent(Parent parent);

	List<Parent> getAllParents();

	int getParentId(String emailId, SecurityRoles role);

	int executeQuery(String query);

	List showAll(String entity);

	Parent updateParent(Parent parent, String loggedInId);

}
