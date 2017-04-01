package edge.app.modules.attendance;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import edge.app.modules.employees.Employee;
import edge.core.modules.common.EdgeEntity;

@Entity
@Table(
		name = "ATTENDANCES",
		uniqueConstraints = {@UniqueConstraint(columnNames = {"employeeId", "attendanceOn"})}
)
public class Attendance extends EdgeEntity{

	private static final long serialVersionUID = 1L;

	@Id
	private String attendanceId;
		
	@ManyToOne
	private Employee employee;
	
	@Column(nullable = false)
	private int employeeId;
	
	@Column(nullable = false, length=100)
	private String employeeName;
	
	@Column(nullable = false)
	private Date attendanceOn;
	
	@Column()
	private int checkInHr;

	@Column()
	private int checkOutHr;
	
	@Column()
	private int hoursWorked;
	
	@Column(nullable = false, length = 200, updatable=false)
	private String details;
	
	@Column(nullable = false, length = 100, updatable=false)
	private String createdBy;
	
	@Column(nullable = false, length = 100)
	private String updatedBy;
	
	@Column(nullable = false, length = 50)
	private int parentId;

	@Column(nullable = false, updatable=false)
	private Date createdOn;
	
	@Column(nullable = false)
	private Date updatedOn;

	public String getAttendanceId() {
		return attendanceId;
	}

	public void setAttendanceId(String attendanceId) {
		this.attendanceId = attendanceId;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		if(employee != null){
			this.employee = employee;
			this.employeeId = employee.getEmployeeId();
			this.employeeName = employee.getName();
		}
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public Date getAttendanceOn() {
		return attendanceOn;
	}

	public void setAttendanceOn(Date attendanceOn) {
		this.attendanceOn = attendanceOn;
	}

	public int getCheckInHr() {
		return checkInHr;
	}

	public void setCheckInHr(int checkInHr) {
		this.checkInHr = checkInHr;
	}

	public int getCheckOutHr() {
		return checkOutHr;
	}

	public void setCheckOutHr(int checkOutHr) {
		this.checkOutHr = checkOutHr;
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

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
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

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public int getHoursWorked() {
		return hoursWorked;
	}

	public void setHoursWorked(int hoursWorked) {
		this.hoursWorked = hoursWorked;
	}
	
}
