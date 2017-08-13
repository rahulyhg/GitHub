package edge.app.modules.lostRequest;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import edge.app.modules.common.IdTypeEnum;
import edge.app.modules.common.RequestStatusEnum;
import edge.app.modules.common.UniqueIdTypeEnum;
import edge.core.modules.common.EdgeEntity;

@Entity
@Table(
		name = "LOSTREQUESTS",
		uniqueConstraints = {@UniqueConstraint(columnNames = {"addressEmail", "matchingKey"})}
)
public class LostRequest extends EdgeEntity{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int lostRequestId;
	
	@Column(nullable = true, length = 50)
	private int foundRequestId;
	
	@Column(nullable = false, length = 50)
	private String idType; // With ID / LOST AND FOUND TAG / NONE
	
	@Column(nullable = false, length = 50)
	private String uniqueIdType;
	
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
	private String status = RequestStatusEnum.UNMATCHED.name();

	@Column(nullable = false, length = 100)
	private String addressLine1;

	@Column(nullable = false, length = 100)
	private String addressLine2;

	@Column(nullable = false, length = 30)
	private String addressCity;
	
	@Column(nullable = false, length = 10)
	private String addressZip;
	
	@Column(nullable = false, length = 30)
	private String addressState;

	@Column(nullable = false, length = 30)
	private String addressCountry;

	@Column(nullable = false, length = 10)
	private String addressPhone;
	
	@Column(nullable = true, length = 10)
	private String addressAltPhone = "";

	@Column(nullable = false, length = 100)
	private String addressEmail;
	
	@Column(nullable = false, length = 250)
	private String matchingKey;

	@Column(nullable = false, updatable=false)
	private Date createdOn;
	
	@Column(nullable = false)
	private Date updatedOn;

	public int getLostRequestId() {
		return lostRequestId;
	}

	public void setLostRequestId(int lostRequestId) {
		this.lostRequestId = lostRequestId;
	}
	
	public LostRequest() {
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

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getAddressCity() {
		return addressCity;
	}

	public void setAddressCity(String addressCity) {
		this.addressCity = addressCity;
	}

	public String getAddressCountry() {
		return addressCountry;
	}

	public void setAddressCountry(String addressCountry) {
		this.addressCountry = addressCountry;
	}

	public String getAddressPhone() {
		return addressPhone;
	}

	public void setAddressPhone(String addressPhone) {
		this.addressPhone = addressPhone;
	}

	public String getAddressAltPhone() {
		return addressAltPhone;
	}

	public void setAddressAltPhone(String addressAltPhone) {
		this.addressAltPhone = addressAltPhone;
	}

	public String getAddressEmail() {
		return addressEmail;
	}

	public void setAddressEmail(String addressEmail) {
		this.addressEmail = addressEmail;
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

	public RequestStatusEnum getStatus() {
		return RequestStatusEnum.valueOf(status);
	}

	public void setStatus(RequestStatusEnum status) {
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

	public String getAddressState() {
		return addressState;
	}

	public void setAddressState(String addressState) {
		this.addressState = addressState;
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

	public int getFoundRequestId() {
		return foundRequestId;
	}

	public void setFoundRequestId(int foundRequestId) {
		this.foundRequestId = foundRequestId;
	}

	public String getAddressZip() {
		return addressZip;
	}

	public void setAddressZip(String addressZip) {
		this.addressZip = addressZip;
	}
	
}
