package edge.core.modules.auth;

public enum SecurityRoles {
	SUPER_ADMIN ("ROLE_SUPER_ADMIN"),
	ADMIN  ("ROLE_ADMIN"),
	USER  ("ROLE_USER"),
	
	PARENT_OPERATOR  ("PARENT_OPERATOR"),
	PARENT_ADMIN  ("PARENT_ADMIN");
	
	private String roleDescription;
	
	private SecurityRoles(String roleDescription) {
		this.setRoleDescription(roleDescription);
	}

	public String getRoleDescription() {
		return roleDescription;
	}

	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}
	
}
