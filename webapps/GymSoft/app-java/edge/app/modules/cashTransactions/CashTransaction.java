package edge.app.modules.cashTransactions;

import java.math.BigDecimal;
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
		name = "CASH_TRANSACTIONS"
)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CashTransaction extends EdgeEntity{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int cashTransactionId;
	
	@Column(nullable = false, length = 50)
	private String transactionType;
	
	@Column(nullable = false)
	private BigDecimal amount;
	
	@Column(nullable = false, length = 100)
	private String details;
	
	@Column(nullable = false, length = 10)
	private String mode;
	
	@Column(nullable = false)
	private BigDecimal balance;
	
	@Column(nullable = false, length = 10)
	private String status = "Draft";
	
	@Column(nullable = false, updatable=false)
	private Date transactiondate;

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

	public int getCashTransactionId() {
		return cashTransactionId;
	}

	public void setCashTransactionId(int cashTransactionId) {
		this.cashTransactionId = cashTransactionId;
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

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Date getTransactiondate() {
		return transactiondate;
	}

	public void setTransactionDate(Date transactiondate) {
		this.transactiondate = transactiondate;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	
}
