package edge.app.modules.packages;

import java.util.List;

public interface PackagesService {

	PackageEntity savePackage(PackageEntity parent, String loggedInId);

	List<PackageEntity> getAllPackages(String loggedInId);
	
	List<PackageEntity> getActivePackages(String loggedInId);

}
