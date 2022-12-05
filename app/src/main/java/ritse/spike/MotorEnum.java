package ritse.spike;

/**
 * The motor enum indicates the port a motor is plugged into
 */
public enum MotorEnum {
	A("A"),
	B("B"),
	C("C"),
	D("D"),
	E("E"),
	F("F");

	/**
	 * can be used to get string value of MotorEnum
	 */
	public final String asString;

	MotorEnum(final String value) {
		this.asString = value;
	}
}
