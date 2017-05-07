package edge.app.modules.allTransactions;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import edge.core.modules.common.EdgeEntity;
import edge.core.utils.CoreDateUtils;

@Entity
@Table(
		name = "ALL_TRANSACTIONS"
)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AllTransaction extends EdgeEntity{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int allTransactionId;
	
	@Column(nullable = false, length = 50)
	private String transactionType;
	
	@Column(nullable = false)
	private BigDecimal amount;
	
	@Column(nullable = false, length = 100)
	private String details;
	
	@Column(nullable = false, length = 10)
	private String mode;  // Credit / Debit
	
	@Column(nullable = false, length = 10)
	private String deskTransactionFlag;  // Yes / No
	
	@Column(nullable = false)
	private BigDecimal allBalance;
	
	@Column(nullable = false)
	private BigDecimal deskCashBalance;
	
	@Column(nullable = false, length = 10)
	private String status;
	
	@Column(nullable = false, updatable=false)
	private Date transactionDate;

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

	public int getAllTransactionId() {
		return allTransactionId;
	}

	public void setAllTransactionId(int allTransactionId) {
		this.allTransactionId = allTransactionId;
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

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactiondate) {
		this.transactionDate = transactiondate;
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

	public String toComment() {
		return " #" + allTransactionId + " - U: " + getUpdatedBy()
				+ "<br>      Transaction Date: " + CoreDateUtils.dateToStandardSting(transactionDate)
				+ "<br>      Transaction Type: " + transactionType
				+ "<br>      Mode: " + mode
				+ "<br>      Transaction Amount: " + amount
				+ "<br>      Desk Cash Balance: " + deskCashBalance
				+ "<br>      All Balance: " + allBalance
				+ "<br>      Details: " + details;
				
	}

	public BigDecimal getAllBalance() {
		return allBalance;
	}

	public void setAllBalance(BigDecimal allBalance) {
		this.allBalance = allBalance;
	}

	public BigDecimal getDeskCashBalance() {
		return deskCashBalance;
	}

	public void setDeskCashBalance(BigDecimal deskCashBalance) {
		this.deskCashBalance = deskCashBalance;
	}

	public String getDeskTransactionFlag() {
		return deskTransactionFlag;
	}

	public void setDeskTransactionFlag(String deskTransactionFlag) {
		this.deskTransactionFlag = deskTransactionFlag;
	}
	
}
