package task.garage.model;

public enum Type {

	CAR("Car"), MOTORCYCLE("MotorCycle");

	private final String text;

	/**
	 * @param text
	 */
	private Type(final String text) {
		this.text = text;
	}

	private String getText() {
		return this.text;
	}

	public static Type getType(String text) {
		if (text != null) {
			for (Type b : Type.values()) {
				if (text.equalsIgnoreCase(b.text)) {
					return b;
				}
			}
		}
		return null;
	}
}