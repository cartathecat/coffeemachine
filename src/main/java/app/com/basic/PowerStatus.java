package app.com.basic;

public enum PowerStatus implements IPowerStatusDescription {

	ON("Coffee machine is ON"),
	OFF("Coffee machine is OFF");
	
	PowerStatus(String s) {
		description = s;
	}

	private String description;
	
	public String getDescription() {
		return description;
	}


}
