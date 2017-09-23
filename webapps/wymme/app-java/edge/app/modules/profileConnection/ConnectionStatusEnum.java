package edge.app.modules.profileConnection;

public enum ConnectionStatusEnum {
	Requested("info"), Accepted("success"), Rejected("danger");
	
	private String category;

	ConnectionStatusEnum(String category){
		this.setCategory(category);
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
}
