package ritse.spike;

/**
 * The button enums
 */
public enum ButtonEnum {
	LEFT("left"),
	RIGHT("right"),
	CENTER("center");

	/**
	 * can be used to get string value of ButtonEnum
	 */
	public String asString;

	ButtonEnum(final String value) {
		this.asString = value;
	}
}
