package model.bundle;

public enum Attributes {
	FORMA_NO("Forma No"), FIRST_NAME("First Name"), LAST_NAME("Last Name"), RATING("Rating");
	
	private String attribute;
	
	Attributes(String attribute) {
		this.setAttribute(attribute);
	}

	public String getAttribute() {
		return attribute;
	}

	private void setAttribute(String attribute) {
		this.attribute = attribute;
	}
}
