package edge.app.modules.clients;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import edge.core.exception.AppException;
import edge.core.modules.common.EdgeEntity;

@Entity
@Table(
		name = "CLIENTS", 
		uniqueConstraints = {@UniqueConstraint(columnNames = {"parentId", "emailId"})}
		/*uniqueConstraints = {
                @UniqueConstraint(columnNames = "phone"),
                @UniqueConstraint(columnNames = "emailId")
        }*/
)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Client extends EdgeEntity{

	private static final long serialVersionUID = 1L;
	private static BigDecimal ZERO = new BigDecimal(0);

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int clientId;
	
	@Column(nullable = false, length = 100)
	private String name;
	
	@Column(nullable = false, length = 10)
	private String gender;
	
	@Column(nullable = false, length = 50)
	private String phone;
	
	@Column(nullable = false, length = 100)
	private String emailId;

	@Column(nullable = false, length = 200)
	private String address;
	
	@Column(nullable = false)
	private BigDecimal totalAmount = ZERO;
	
	@Column(nullable = false)
	private BigDecimal paidAmount = ZERO;

	@Column(nullable = false)
	private BigDecimal balanceAmount = ZERO;
	
	@Column()
	private Date lastPaidOn;
	
	@Column(nullable = false)
	private Date reminderOn = new Date();
	
	@Column(nullable = false, length = 100)
	private String reminderAbout;
	
	@Column(nullable = false, length = 200)
	private String profilePic="NotAvailable";
	
	@Transient
	private String comment;
	
	@Column(length = 2000)
	private String activity;
	
	@Column()
	private Date membershipEndDate;
	
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
	
	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId.toLowerCase();
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
	
	public String getDisplay(){
		return name + " : " + emailId  + " : " + clientId ;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(BigDecimal paidAmount) {
		this.paidAmount = paidAmount;
	}

	public Date getLastPaidOn() {
		return lastPaidOn;
	}

	public void setLastPaidOn(Date lastPaidOn) {
		this.lastPaidOn = lastPaidOn;
	}

	public BigDecimal getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(BigDecimal balanceAmount) {
		if(balanceAmount.signum() == -1){
			throw new AppException(null, "Invalid Paid Amount.");
		}
		this.balanceAmount = balanceAmount;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public Date getMembershipEndDate() {
		return membershipEndDate;
	}

	public void setMembershipEndDate(Date membershipEndDate) {
		this.membershipEndDate = membershipEndDate;
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

	public Date getReminderOn() {
		return reminderOn;
	}

	public void setReminderOn(Date reminderOn) {
		this.reminderOn = reminderOn;
	}

	public String getReminderAbout() {
		return reminderAbout;
	}

	public void setReminderAbout(String reminderAbout) {
		this.reminderAbout = reminderAbout;
	}

	public String getActivity() {
		return activity;
	}
	
	public void setActivity(String activity) {
		this.activity = activity;
	}
	
	public void addComment(String comment, String loggedInId) {
		Date today = new Date();
		if(activity == null){
			activity = " <B> " + today  + " : U - " + loggedInId + "</B>"
					    + "<br>      " + comment;
		}else{
			activity =  " <B> " + today  + " : U - " + loggedInId + "</B>"
				    	+ ":<br>      " + comment + "; <br><br>" + activity;
		}
	}
	
	public void setNextReminderOn(Date date) {
		setReminderOn(date);
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public String getComment() {
		return comment;
	}

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}
	
	/*
	public void setComment(String comment) {
		Date today = new Date();
		if(activity == null){
			activity = " " + today + " :<br>      " + comment;
		}else{
			activity = " " + today + " :<br>      " + comment + "; <br><br>" + activity;
		}
	}*/
}
