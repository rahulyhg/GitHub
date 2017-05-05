package edge.core.modules.parents;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import edge.core.modules.common.EdgeEntity;

@Entity
@Table(
		name = "PARENTS", 
		uniqueConstraints = {@UniqueConstraint(columnNames = {"emailId"})}
)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Parent extends EdgeEntity{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int parentId;
	
	@Column(nullable = false, length = 100)
	private String name;
	
	@Column(nullable = false, length = 50)
	private String phone;

	@Column(nullable = false, length = 100)
	private String emailId;
	
	@Column(nullable = false, length = 500)
	private String operators;

	@Column(nullable = false, length = 500)
	private String admins;

	@Column(nullable = false, length = 200)
	private String logo;
	
	@Column(nullable = false, length = 500)
	private String address;
	
	@Column(nullable = false)
	private Date fromDate;
	
	@Column(nullable = false)
	private Date toDate;
	
	@Column(nullable = false, length = 100, updatable=false)
	private String createdBy;
	
	@Column(nullable = false, length = 100)
	private String updatedBy;

	@Column(nullable = false, updatable=false)
	private Date createdOn;
	
	@Column(nullable = false)
	private Date updatedOn;
	
	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getOperators() {
		return operators;
	}

	public void setOperators(String operators) {
		operators = operators.replace(" ", "");
		char charAt = operators.charAt(0);
		if(charAt != ','){
			this.operators = "," + operators + ",";
		}
	}

	public String getAdmins() {
		return admins;
	}

	public void setAdmins(String admins) {
		admins = admins.replace(" ", "");
		char charAt = admins.charAt(0);
		if(charAt != ','){
			this.admins = "," + admins + ",";
		}
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
		this.createdOn = new Date();
		this.updatedOn = new Date();
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
		this.updatedOn = new Date();
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
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

	public String getDisplay(){
		return name + " : " + emailId  + " : " + parentId + " ~~~ " ;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	
}
