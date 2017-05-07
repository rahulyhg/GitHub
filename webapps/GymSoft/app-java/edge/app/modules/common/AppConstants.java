package edge.app.modules.common;

public class AppConstants {

	public static final long MAX_DAYS_NEXT_REMINDER = 120;
	
	public static enum EntityStatus{
		
		DRAFT("Draft"), ACTIVE("Active"), INACTIVE("Inactive"), APPROVED("Approved"), REJECTED("Rejected"), SYSTEM("System");
		
		private String status;
		
		private EntityStatus(String status) {
			this.status = status;
		}
		
		public String getStatus() {
			return status;
		}
		
	}
	
	public static enum Reminder{
		
		ENQUIRY("Enquiry"), BALANCE("Balance Due"), RENEWAL("Renewal"), PAYMENT_REJECTED("Payment Rejected");
		
		private String description;
		
		private Reminder(String description) {
			this.description = description;
		}
		
		public String getDescription() {
			return description;
		}
		
	}
	
}
