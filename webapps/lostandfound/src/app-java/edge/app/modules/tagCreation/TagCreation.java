package edge.app.modules.tagCreation;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import edge.core.modules.common.EdgeEntity;

@Entity
@Table(
		name = "TAGCREATIONS",
		uniqueConstraints = {@UniqueConstraint(columnNames = {"addressEmail"})}
)
public class TagCreation extends EdgeEntity{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int tagCreationId;

	@Column(nullable = false, length = 100)
	private String fullName;
	
	@Column(nullable = true, length = 100)
	private String addressLine1 = "";

	@Column(nullable = true, length = 100)
	private String addressLine2 = "";

	@Column(nullable = true, length = 30)
	private String addressCity = "";
	
	@Column(nullable = true, length = 10)
	private String addressZip = "";
	
	@Column(nullable = true, length = 30)
	private String addressState = "";

	@Column(nullable = true, length = 30)
	private String addressCountry = "";

	@Column(nullable = true, length = 10)
	private String addressPhone = "";
	
	@Column(nullable = true, length = 10)
	private String addressAltPhone = "";

	@Column(nullable = false, length = 100)
	private String addressEmail = "";
	
	@Column(nullable = true, updatable=false)
	private Date createdOn;
	
	@Column(nullable = true)
	private Date updatedOn;

	public int getTagCreationId() {
		return tagCreationId;
	}

	public void setTagCreationId(int tagCreationId) {
		this.tagCreationId = tagCreationId;
	}
	
	public TagCreation() {
		// TODO Auto-generated constructor stub
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
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

	public String getAddressZip() {
		return addressZip;
	}

	public void setAddressZip(String addressZip) {
		this.addressZip = addressZip;
	}

	public String getAddressState() {
		return addressState;
	}

	public void setAddressState(String addressState) {
		this.addressState = addressState;
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
	
}
