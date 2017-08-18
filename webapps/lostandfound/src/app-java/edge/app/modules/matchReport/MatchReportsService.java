package edge.app.modules.matchReport;

import java.util.List;

import edge.app.modules.foundReport.FoundReport;
import edge.app.modules.lostReport.LostReport;

public interface MatchReportsService {

	List<FoundReport> searchMatchingReports(Long lostReportId) throws Exception;
	
	List<FoundReport> searchMatchingReports(LostReport lostReport) throws Exception;

	List<LostReport> searchMatchingReports(FoundReport foundReport) throws Exception;

	List<FoundReport> searchMatchingReportsAsPerLFI(Long lostAndFoundId) throws Exception;

}
