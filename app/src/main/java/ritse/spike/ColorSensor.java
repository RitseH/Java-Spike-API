package ritse.spike;

import static java.lang.String.format;

import java.io.IOException;

/**
 * Represents a color sensor on the mindstorms hub
 */
public class ColorSensor {

	/**
	 * the spike command executor
	 */
	private final SpikeCommandExecutor spikeCommandExecutor;

	/**
	 * Constructor.
	 *
	 * @param spikeCommandExecutor the spike command executor
	 */
	public ColorSensor(final SpikeCommandExecutor spikeCommandExecutor) {
		this.spikeCommandExecutor = spikeCommandExecutor;
	}

	/** ACTIONS */

	/**
	 * Lights up all lights with certain brightness
	 *
	 * @param brightness the brightness
	 * @throws IOException when executing command fails
	 */
	public void lightUpAll(final int brightness) throws IOException {
		spikeCommandExecutor.executeVoid(format("color_sensor.light_up_all(" + brightness + ")"));
	}

	/**
	 * Lights up all light with possible different brightnesses.
	 *
	 * @param brightnessLight1 brightness of light 1
	 * @param brightnessLight2 brightness of light 2
	 * @param brightnessLight3 brightness of light 3
	 * @throws IOException when executing command fails
	 */
	public void lightUp(final int brightnessLight1, final int brightnessLight2, final int brightnessLight3) throws IOException {
		spikeCommandExecutor.executeVoid(format("color_sensor.light_up(%d, %d, %d)", brightnessLight1, brightnessLight2, brightnessLight3));
	}

	/** EVENTS */

	/**
	 * Event for waiting for a certain color
	 *
	 * @param color the color
	 * @throws IOException when executing the command fails
	 */
	public void waitUntilColor(final String color) throws IOException {
		spikeCommandExecutor.executeVoid(format("color_sensor.wait_until_color('%s')", color));
	}

	/**
	 * Event for waiting for a new color
	 *
	 * @param color the color
	 * @throws IOException when executing the command fails
	 */
	public void waitForNewColor(final String color) throws IOException {
		spikeCommandExecutor.executeVoid(format("color_sensor.wait_for_new_color('%s')", color));
	}

	/** MEASUREMENTS */

	/**
	 * Method for getting the color the sensor reads
	 *
	 * @return the color
	 * @throws IOException          when executing command fails
	 * @throws InterruptedException when the thread is interrupted, ocupied, sleeping during the activity
	 */
	public String getColor() throws InterruptedException, IOException {
		final String result = spikeCommandExecutor.execute(format("color_sensor.get_color()"));
		if (result.equals("None")) {
			return "0";
		}
		return result;
	}

	/**
	 * Gets the ambient light
	 *
	 * @return the ambient light
	 * @throws IOException          when executing command fails
	 * @throws InterruptedException when the thread is interrupted, ocupied, sleeping during the activity
	 */
	public int getAmbientLight() throws IOException, InterruptedException {
		final String result = spikeCommandExecutor.execute(format("color_sensor.get_ambient_light()"));
		if (result.equals("None")) {
			return 0;
		}
		return Integer.parseInt(result);
	}

	/**
	 * Gets the reflected light
	 *
	 * @return the reflected light
	 * @throws IOException          when executing command fails
	 * @throws InterruptedException when the thread is interrupted, ocupied, sleeping during the activity
	 */
	public int getReflectedLight() throws IOException, InterruptedException {
		final String result = spikeCommandExecutor.execute(format("color_sensor.get_reflected_light()"));
		if (result.equals("None")) {
			return 0;
		}
		return Integer.parseInt(result);
	}
}
