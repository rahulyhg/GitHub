package edge.app.modules.payments;

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

import edge.app.modules.clients.Client;
import edge.app.modules.common.AppConstants;
import edge.core.modules.common.EdgeEntity;
import edge.core.utils.CoreDateUtils;

@Entity
@Table(
		name = "PAYMENTS",
		uniqueConstraints = {@UniqueConstraint(columnNames = {"clientId", "paidOn", "pymtMode"})}
)
public class Payment extends EdgeEntity{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int paymentId;
	
	@ManyToOne
	private Client client;
	
	// Added for uniqueness
	@Column(nullable = false, length = 50)
	private int clientId;
		
	@Column(nullable = false)
	private BigDecimal paidAmount;
	
	@Column(nullable = false)
	private Date paidOn;
	
	@Column(nullable = false, length = 20)
	private String pymtMode;
	
	@Column(nullable = false, length = 50)
	private String referenceNo;
	
	@Column(nullable = false, length = 200)
	private String details;

	@Column(nullable = false, length = 20)
	private String status = AppConstants.EntityStatus.DRAFT.getStatus();
	
	@Column(nullable = false, length = 100, updatable=false)
	private String createdBy;
	
	@Column(nullable = false, length = 100)
	private String updatedBy;

	@Column(nullable = false, updatable=false)
	private Date createdOn;
	
	@Column(nullable = false)
	private Date updatedOn;
	
	@Column(nullable = false, length = 50)
	private int parentId;
	
	public int getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}

	public BigDecimal getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(BigDecimal paidAmount) {
		this.paidAmount = paidAmount;
	}

	public Date getPaidOn() {
		return paidOn;
	}

	public void setPaidOn(Date paidOn) {
		this.paidOn = paidOn;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		if(client != null){
			this.client = client;
			this.setClientId(client.getClientId());
		}
	}

	public String getPymtMode() {
		return pymtMode;
	}

	public void setPymtMode(String pymtMode) {
		this.pymtMode = pymtMode;
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

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
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

	public String toComment() {
		return " #" + paymentId + " - U: " + getUpdatedBy()
				+ "<br>      Received " + paidAmount + " on " + CoreDateUtils.dateToStandardSting(paidOn) + " through " + pymtMode;
				//+ "<br>      " + details
				//+ "<br>      Created By " + getUpdatedBy()
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}
	
}
