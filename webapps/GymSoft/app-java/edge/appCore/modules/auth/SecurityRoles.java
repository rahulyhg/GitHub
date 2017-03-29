package edge.appCore.modules.auth;

public enum SecurityRoles {
	SUPER_ADMIN ("ROLE_SUPER_ADMIN"),
	ADMIN  ("ROLE_ADMIN"),
	USER  ("ROLE_USER"),
	
	GYM_OPERATOR  ("GYM_OPERATOR"),
	GYM_ADMIN  ("GYM_ADMIN");
	
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
