package edge.app.modules.memberships;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang.time.DateUtils;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import edge.app.modules.clients.Client;
import edge.app.modules.employees.Employee;
import edge.core.modules.common.EdgeEntity;
import edge.core.utils.CoreDateUtils;

@Entity
@Table(
		name = "MEMBERSHIPS",
		uniqueConstraints = {@UniqueConstraint(columnNames = {"clientId", "packageName", "fromDate","systemId"})}
)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Membership extends EdgeEntity{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int membershipId;
	
	@ManyToOne
	//@Column(nullable = false, length = 6)
	private Client client;
	
	// Added for uniqueness
	@Column(nullable = false, length = 50)
	private int clientId;
	
	@Column(nullable = false, length = 50)
	private String packageName;
	
	@Column(nullable = false)
	private Date fromDate;
	
	@Column(nullable = false)
	private Date toDate;
	
	@Column(nullable = false)
	private BigDecimal totalAmount;
	
	@Column(nullable = false)
	private BigDecimal discountAmount;
	
	@Column(nullable = false)
	private BigDecimal effectiveAmount;

	@ManyToOne
	private Employee collectionBy;
	
	@Column(nullable = false, length = 100)
	private String collectionByName;
	
	@Column(nullable = false, length = 100, updatable=false)
	private String createdBy;
	
	@Column(nullable = false, length = 100)
	private String updatedBy;

	@Column(nullable = false, updatable=false)
	private Date createdOn;
	
	@Column(nullable = false)
	private Date updatedOn;
	
	@Column(nullable = false, length = 50)
	private int systemId;
	
	public int getMembershipId() {
		return membershipId;
	}

	public void setMembershipId(int membershipId) {
		this.membershipId = membershipId;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		if(client != null){
			this.client = client;
			this.clientId = client.getClientId();
		}
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
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

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(BigDecimal discountAmount) {
		this.discountAmount = discountAmount;
	}

	public BigDecimal getEffectiveAmount() {
		return effectiveAmount;
	}

	public void setEffectiveAmount(BigDecimal effectiveAmount) {
		this.effectiveAmount = effectiveAmount;
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

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public int getSystemId() {
		return systemId;
	}

	public void setSystemId(int systemId) {
		this.systemId = systemId;
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

	public Employee getCollectionBy() {
		return collectionBy;
	}

	public void setCollectionBy(Employee collectionBy) {
		if(collectionBy != null){
			this.collectionBy = collectionBy;
			this.collectionByName= collectionBy.getName();
		}
	}

	public String getCollectionByName() {
		return collectionByName;
	}

	public void setCollectionByName(String collectionByName) {
		this.collectionByName = collectionByName;
	}
	
	public String toComment() {
		return membershipId + "; C: " + getCollectionByName() + " - U: " + getUpdatedBy()
				+ "<br>      Package: " + packageName + " ( " + CoreDateUtils.dateToStandardSting(fromDate) + " - " + CoreDateUtils.dateToStandardSting(toDate)  + " ) "
				+ "<br>      Amount: " + totalAmount + " - " + discountAmount + " = " + effectiveAmount;
	}

}
