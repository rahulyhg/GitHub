package edge.app.modules.foundReport;

import java.util.List;

import edge.app.modules.lostReport.LostReport;

public interface FoundReportsService {

	FoundReport saveFoundReport(FoundReport foundReport);

	FoundReport getFoundReport(int foundReportId);

	List<FoundReport> searchMatchingReports(int lostReportId) throws Exception;
	
	List<FoundReport> searchMatchingReports(LostReport lostReport) throws Exception;

	List<LostReport> searchMatchingReports(FoundReport foundReport) throws Exception;

	List<FoundReport> searchMatchingReportsAsPerLFI(String lostAndFoundId) throws Exception;

}
