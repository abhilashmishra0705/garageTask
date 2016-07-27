package task.garage.model;

import java.util.Map;

public class LevelInformation {

	private String level;
	private Map<Type, String> typeInformation;

	/**
	 * @return the level
	 */
	public String getLevel() {
		return level;
	}

	/**
	 * @param level
	 *            the level to set
	 */
	public void setLevel(String level) {
		this.level = level;
	}

	/**
	 * @return the typeInformation
	 */
	public Map<Type, String> getTypeInformation() {
		return typeInformation;
	}

	/**
	 * @param typeInformation
	 *            the typeInformation to set
	 */
	public void setTypeInformation(Map<Type, String> typeInformation) {
		this.typeInformation = typeInformation;
	}
}
