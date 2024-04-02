package util;

public enum UserType {
	
	REFEREE("Arbitro"),
	PLAYER("Giocatore"),
	ADMIN("Organizzatore");
	
	private final String label;
	
	private UserType(final String lbl) {
		this.label = lbl;
	}
	
	public String getLabel() {
		return this.label;
	}
}
