package edge.app.modules.appReport;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import edge.core.modules.common.EdgeEntity;

@Entity
@Table(
		name = "APP_REPORT"
)
public class AppReport extends EdgeEntity{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long appReportId;

	@Column(nullable = true, length = 50)
	private Long reportsMatched;

	@Column(nullable = false)
	private Date updatedOn;

	public Long getAppReportId() {
		return appReportId;
	}

	public void setAppReportId(Long appReportId) {
		this.appReportId = appReportId;
	}

	public Long getReportsMatched() {
		return reportsMatched;
	}

	public void setReportsMatched(Long reportsMatched) {
		this.reportsMatched = reportsMatched;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	
}
