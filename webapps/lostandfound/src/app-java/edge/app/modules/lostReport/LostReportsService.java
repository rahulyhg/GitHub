package edge.app.modules.lostReport;


public interface LostReportsService {

	LostReport saveLostReport(LostReport lostReport);

	LostReport getLostReport(int lostReportId);

}
