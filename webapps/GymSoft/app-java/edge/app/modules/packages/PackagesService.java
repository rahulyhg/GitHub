package edge.app.modules.packages;

import java.util.List;

public interface PackagesService {

	Package savePackage(Package gym, String loggedInId);

	List<Package> getAllPackages(String loggedInId);
	
	List<Package> getActivePackages(String loggedInId);

}
