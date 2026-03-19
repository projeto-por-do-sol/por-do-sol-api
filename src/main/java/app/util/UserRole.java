package app.util;

public enum UserRole {
	ADMIN("admin"),
	PROPRIETARIO("proprietario"),
	CLIENTE("cliente"),
	ENTREGADOR("entregador");
	
	private String role;
	
	UserRole(String role){
		this.role = role;
	}
	
	public String getRole() {
		return role;
	}
}
