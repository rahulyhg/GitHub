package edge.core.modules.parents;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import edge.core.modules.common.EdgeEntity;

@Entity
@Table(
		name = "PARENTS_DATA", 
		uniqueConstraints = {@UniqueConstraint(columnNames = {"parentId"})}
)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ParentData extends EdgeEntity{

	private static final long serialVersionUID = 1L;

	@Id
	private int parentId;
	
	@Column(nullable = false)
	private BigDecimal deskCashBalance;
		
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

	public BigDecimal getDeskCashBalance() {
		return deskCashBalance;
	}

	public void setDeskCashBalance(BigDecimal deskCashBalance) {
		this.deskCashBalance = deskCashBalance;
	}
	
}
