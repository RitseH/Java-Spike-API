package ritse.spike;

import java.io.IOException;

/**
 * Interface of a mindstorms hub
 */
public interface MindstormsHub {

	/**
	 * Initializes the mindstorms hub
	 *
	 * @throws IOException when executing command failed
	 */
	void initialize() throws IOException;

	/**
	 * Displays text on mindstorms hub screen
	 *
	 * @param text the text that is going to be displayed
	 * @throws IOException when executing command failed
	 */
	void displayText(final String text) throws IOException;

	/**
	 * This method creates a motor
	 *
	 * @param motorEnum the motor enum
	 * @throws IOException when executing command failed
	 */
	void createMotor(final MotorEnum motorEnum) throws IOException;

	/**
	 * This method returns a motor object based on motor enum
	 *
	 * @param motorEnum the motor enum
	 * @return the motor
	 */
	Motor getMotorByMotorEnum(final MotorEnum motorEnum);

	/**
	 * Creates a distance sensor on the mindstorms hub assigned to a port
	 *
	 * @param portChar the port char
	 * @throws IOException when executing command failed
	 */
	void createDistanceSensor(final String portChar) throws IOException;

	/**
	 * Gets the distance sensor object
	 *
	 * @return the distance sensor object
	 */
	DistanceSensor getDistanceSensor();


	/**
	 * Creates a color sensor on the mindstorms hub assigned to a port
	 *
	 * @param portChar the port char
	 *
	 * @throws IOException when executing command failed
	 */
	void createColorSensor(final String portChar) throws IOException;

	/**
	 * Gets the color sensor object
	 *
	 * @return the color sensor object
	 */
	ColorSensor getColorSensor();


	/**
	 * Gets the button of a mindstormshub based on the button enum
	 *
	 * @param buttonEnum the button enum
	 *
	 * @return the button enum
	 */
	Button getButtonByEnum(final ButtonEnum buttonEnum);

	/**
	 * This method creates a motor on the mindstorms hub
	 *
	 * @param motorEnum the motor enum
	 */
	void handleAddMotor(final MotorEnum motorEnum);

	/**
	 * Initializes the evaluator function on the mindstorms hub
	 *
	 * @throws IOException when executing command failed
	 */
	void initializeEvalFunction() throws IOException;
}