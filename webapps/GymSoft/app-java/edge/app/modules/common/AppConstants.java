package edge.app.modules.common;

public class AppConstants {

	public static final long MAX_DAYS_NEXT_REMINDER = 120;
	
	public static enum EntityStatus{
		
		DRAFT("Draft"), ACTIVE("Active"), INACTIVE("Inactive"), APPROVED("Approved"), SYSTEM("System");
		
		private String status;
		
		private EntityStatus(String status) {
			this.status = status;
		}
		
		public String getStatus() {
			return status;
		}
		
	}
	
}
