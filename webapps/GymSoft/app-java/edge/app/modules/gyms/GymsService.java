package edge.app.modules.gyms;

import java.util.List;

import edge.appCore.modules.auth.SecurityRoles;

public interface GymsService {

	Gym addGym(Gym gym);

	List<Gym> getAllGyms();

	int getSystemId(String emailId, SecurityRoles role);

	int executeQuery(String query);

	List showAll(String entity);

	Gym updateGym(Gym gym, String loggedInId);

}
