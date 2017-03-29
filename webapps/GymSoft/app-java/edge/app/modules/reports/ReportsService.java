package edge.app.modules.reports;

import java.util.List;

public interface ReportsService {

	List getMembershipsReport(String loggedInId);
	
	List getPaymentsReport(String loggedInId);
	
	List getAttendancesReport(String loggedInId);

	List getExpensesReport(String loggedInId);
}
