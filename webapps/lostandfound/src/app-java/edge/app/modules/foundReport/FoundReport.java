package edge.app.modules.foundReport;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import edge.app.modules.common.IdTypeEnum;
import edge.app.modules.common.ReportStatusEnum;
import edge.app.modules.common.UniqueIdTypeEnum;
import edge.core.modules.common.EdgeEntity;

@Entity
@Table(
		name = "FOUNDREPORTS",
		uniqueConstraints = {@UniqueConstraint(columnNames = {"addressEmail", "matchingKey"})}
)
public class FoundReport extends EdgeEntity{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long foundReportId;

	@Column(nullable = true, length = 50)
	private Long lostReportId;
	
	@Column(nullable = false, length = 50)
	private String idType; // With ID / LOST AND FOUND TAG / NONE
	
	@Column(nullable = false, length = 50)
	private String uniqueIdType;
	
	@Column(nullable = false, length = 50)
	private Long lostAndFoundId;
	
	@Column(nullable = false, length = 50)
	private String uniqueId;

	@Column(nullable = false)
	private int month;
	
	@Column(nullable = false)
	private int year;

	@Column(nullable = false, length = 50)
	private String city;

	@Column(nullable = false, length = 50)
	private String brandName;
	
	@Column(nullable = false, length = 250)
	private String description;

	@Column(nullable = false, length = 100)
	private String fullName;
	
	@Column(nullable = false, length = 50)
	private String status = ReportStatusEnum.UNMATCHED.name();

	@Column(nullable = true, length = 10)
	private String addressPhone = "";
	
	@Column(nullable = false, length = 100)
	private String addressEmail;
	
	@Column(nullable = false, length = 250)
	private String matchingKey;

	@Column(nullable = false, updatable=false)
	private Date createdOn;
	
	@Column(nullable = false)
	private Date updatedOn;

	public Long getFoundReportId() {
		return foundReportId;
	}

	public void setFoundReportId(Long foundReportId) {
		this.foundReportId = foundReportId;
	}
	
	public FoundReport() {
		// TODO Auto-generated constructor stub
	}

	public IdTypeEnum getIdType() {
		return IdTypeEnum.valueOf(idType);
	}

	public void setIdType(IdTypeEnum idType) {
		this.idType = idType.name();
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAddressPhone() {
		return addressPhone;
	}

	public void setAddressPhone(String addressPhone) {
		this.addressPhone = addressPhone;
	}

	public String getAddressEmail() {
		return addressEmail;
	}

	public void setAddressEmail(String addressEmail) {
		if(addressEmail != null){
			this.addressEmail = addressEmail.trim().toLowerCase();
		}
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public ReportStatusEnum getStatus() {
		return ReportStatusEnum.valueOf(status);
	}

	public void setStatus(ReportStatusEnum status) {
		this.status = status.name();
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public UniqueIdTypeEnum getUniqueIdType() {
		return UniqueIdTypeEnum.valueOf(uniqueIdType);
	}

	public void setUniqueIdType(UniqueIdTypeEnum uniqueIdType) {
		this.uniqueIdType = uniqueIdType.name();
	}

	public String getMatchingKey() {
		return matchingKey;
	}

	public void setMatchingKey(String matchingKey) {
		this.matchingKey = matchingKey;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public Long getLostAndFoundId() {
		return lostAndFoundId;
	}

	public void setLostAndFoundId(Long lostAndFoundId) {
		this.lostAndFoundId = lostAndFoundId;
	}

	public Long getLostReportId() {
		return lostReportId;
	}

	public void setLostReportId(Long lostReportId) {
		this.lostReportId = lostReportId;
	}
	
}
