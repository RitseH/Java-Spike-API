package ritse.spike;

import static java.lang.String.format;

import java.io.IOException;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * A class that represents the distance sensor on a mindstorms hub
 */
public class DistanceSensor {

	/**
	 * The spike command executor
	 */
	private final SpikeCommandExecutor spikeCommandExecutor;

	private int desiredResult = 10;

	/**
	 * Constructor.
	 *
	 * @param executor        the executor
	 */
	public DistanceSensor(final SpikeCommandExecutor executor) {
		this.spikeCommandExecutor = executor;

	}

	/** ACTIONS*/

	/**
	 * Lights up all lights on the distance sensor
	 *
	 * @param brightness the brightness of the lights
	 * @throws IOException when executing the command fails
	 */
	public void lightUpAll(final int brightness) throws IOException {
		spikeCommandExecutor.executeVoid(format("distance_sensor.light_up_all(%d)", brightness));
	}

	/**
	 * Lights up all lights on the distance sensor
	 *
	 * @throws IOException when executing the command fails
	 */
	public void lightUpAll() throws IOException {
		spikeCommandExecutor.executeVoid(format("distance_sensor.light_up_all()"));
	}


	/**
	 * Lights up all lights with possible diffirent values
	 *
	 * @param rightTop    the right top light
	 * @param rightBottom the right bottom light
	 * @param leftBottom  the left bottom light
	 * @param leftTop     the left top light
	 * @throws IOException when executing the command fails
	 */
	public void lightUp(final int rightTop, final int rightBottom, final int leftBottom, final int leftTop) throws IOException {
		spikeCommandExecutor.executeVoid(format("distance_sensor.light_up(%d, %d, %d, %d)", rightTop, rightBottom, leftBottom, leftTop));
	}

	/** GETTERS */

	/**
	 * Gets the distance in centimeters
	 *
	 * @return the distance in centimeters
	 * @throws IOException          when executing command fails
	 * @throws InterruptedException when the thread is interrupted, ocupied, sleeping during the activity
	 */
	public int getDistanceCm() throws IOException, InterruptedException {
		final String result = spikeCommandExecutor.execute(format("distance_sensor.get_distance_cm()"));
		if (result.equals("None")) {
			return 1000;
		}
		return Integer.parseInt(result);
	}

	/**
	 * Gets the distance in inches
	 *
	 * @return the distance in inches
	 * @throws IOException          when executing command fails
	 * @throws InterruptedException when the thread is interrupted, ocupied, sleeping during the activity
	 */
	public int getDistanceInches() throws IOException, InterruptedException {
		final String result = spikeCommandExecutor.execute(format("distance_sensor.get_distance_inches()"));
		if (result.equals("None")) {
			return 1000;
		}
		return Integer.parseInt(result);
	}

	/**
	 * Gets the distance in percentages
	 *
	 * @return the distance in percetages
	 * @throws IOException          when executing command fails
	 * @throws InterruptedException when the thread is interrupted, ocupied, sleeping during the activity
	 */
	public int getDistancePercentage() throws InterruptedException, IOException {
		final String result = spikeCommandExecutor.execute(format("distance_sensor.get_distance_percentage()"));
		if (result.equals("None")) {
			return 1000;
		}
		return Integer.parseInt(result);
	}

	/** EVENTS */

	/**
	 * Event that triggers when distance is farther than a given distance
	 *
	 * @param distance the distance
	 * @throws IOException          when executing command fails
	 * @throws InterruptedException when the thread is interrupted, ocupied, sleeping during the activity
	 */
	public void waitForDistanceFartherThan(final float distance) throws IOException, InterruptedException {
		if (distance >= desiredResult && distance != 1000) {
			System.out.println("We got the distance sensor further than" + distance);
		}
	}

	/**
	 * Event that triggers when distance is closer than a given distance
	 *
	 * @param distance the  distance
	 * @throws IOException          when executing command fails
	 * @throws InterruptedException when the thread is interrupted, ocupied, sleeping during the activity
	 */
	public void waitForDistanceCloserThan(final float distance) throws IOException, InterruptedException {
		if (distance <= desiredResult) {
			System.out.println("We got the distance sensor closer than" + distance);
		}
	}


	/**
	 * Sets the desired result member variable
	 *
	 * @param desiredResult the desired result
	 */
	public void setDesiredResult(final int desiredResult) {
		this.desiredResult = desiredResult;
	}
}