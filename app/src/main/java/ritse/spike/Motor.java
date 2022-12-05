package ritse.spike;

import static java.lang.String.format;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Class that represents a Motor on a mindstorms hub
 */
public class Motor {

	/**
	 * The motor enum
	 */
	private final MotorEnum motorEnum;

	/**
	 * The spike command executor
	 */
	private final SpikeCommandExecutor spikeCommandExecutor;

	/**
	 * Constructor.
	 *
	 * @param motorEnum the motor enum
	 * @param executor the command executor
	 */
	public Motor(final MotorEnum motorEnum, final SpikeCommandExecutor executor) {
		this.spikeCommandExecutor = executor;
		this.motorEnum = motorEnum;
	}

	/**
	 * ACTIONS
	 */

	/**
	 * Runs the motor for x seconds
	 *
	 * @param time the time the motor needs to run
	 *
	 * @throws IOException when executing command fails
	 */
	public void runForSeconds(final int time) throws IOException {
		spikeCommandExecutor.executeVoid(format("motor%s.run_for_seconds(" + time + ")", motorEnum.asString));
	}

	/**
	 * Runs the motor for certain amount of degrees
	 *
	 * @param degrees the degrees
	 *
	 * @throws IOException when executing the command fails
	 */
	public void runForDegrees(final int degrees) throws IOException {
		spikeCommandExecutor.executeVoid(format("motor%s.run_for_degrees(" + degrees + ")", motorEnum.asString));
	}

	/**
	 * Runs the motor to a cetrain position
	 *
	 * @param position the position to run the motor to
	 *
	 * @throws IOException when executing the command fails
	 */
	public void runToPosition(final int position) throws IOException {
		spikeCommandExecutor.executeVoid(format("motor%s.run_to_position(" + position + ")", motorEnum.asString));
	}

	/**
	 * Runs the motor to a certain degrees counted
	 *
	 * @param degrees the degrees counted
	 *
	 * @throws IOException when executing the command fails
	 */
	public void runToDegreesCounted(final int degrees) throws IOException {
		spikeCommandExecutor.executeVoid(format("motor%s.run_to_degrees_counted(" + degrees + ")", motorEnum.asString));
	}

	/**
	 * Runs the motor for a certain amount of rotations
	 *
	 * @param rotations the amount of rotations
	 *
	 * @throws IOException when executing the command fails
	 */
	public void runForRotations(final float rotations) throws IOException {
		spikeCommandExecutor.executeVoid(format("motor%s.run_for_rotations(" + rotations + ")", motorEnum.asString));
	}

	/**
	 * Command for starting the motor at a certain speed
	 *
	 * @param speed the speed at which the motor needs to start
	 *
	 * @throws IOException when executing the command fails
	 */
	public void start(final int speed) throws IOException {
		spikeCommandExecutor.executeVoid(format("motor%s.start(" + speed + ")", motorEnum.asString));
	}

	/**
	 * Command for starting the motor
	 *
	 * @throws IOException when executing the command fails
	 */
	public void start() throws IOException {
		spikeCommandExecutor.executeVoid(format("motor%s.start()", motorEnum.asString));
	}

	/**
	 * Stops the motor
	 *
	 * @throws IOException when executing command fails
	 */
	public void stop() throws IOException {
		spikeCommandExecutor.executeVoid(format("motor%s.stop()", motorEnum.asString));
	}

	/**
	 * This method sets the start power of the motor
	 *
	 * @param power the power
	 *
	 * @throws IOException when executing command fails
	 */
	public void startAtPower(final int power) throws IOException {
		spikeCommandExecutor.executeVoid(format("motor%s.start_at_power(" + power + ")", motorEnum.asString));
	}


	/**
	 * GETTERS / MEASUREMENTS
	 */

	/**
	 * Gets the speed of the motor
	 *
	 * @return the speed of the motor
	 *
	 * @throws IOException when executing command fails
	 * @throws InterruptedException when the thread is interrupted, ocupied, sleeping during the activity
	 */
	public int getSpeed() throws IOException, InterruptedException {
		return Integer.parseInt(spikeCommandExecutor.execute(format("motor%s.get_speed()", motorEnum.asString)));
	}

	/**
	 * Gets the position of the motor
	 *
	 * @return the motor position
	 *
	 * @throws IOException when executing command fails
	 * @throws InterruptedException when the thread is interrupted, ocupied, sleeping during the activity
	 */
	public int getPosition() throws IOException, InterruptedException {
		return Integer.parseInt(spikeCommandExecutor.execute(format("motor%s.get_position()", motorEnum.asString)));
	}

	/**
	 * Gets the degrees counted of the motor
	 *
	 * @return the degrees counted of the motor
	 *
	 * @throws IOException when executing command fails
	 * @throws InterruptedException when the thread is interrupted, ocupied, sleeping during the activity
	 */
	public int getDegreesCounted() throws IOException, InterruptedException {
		return Integer.parseInt(spikeCommandExecutor.execute(format("motor%s.get_degrees_counted()", motorEnum.asString)));
	}

	/**
	 * Gets the default speed of the motor
	 *
	 * @return the default speed of the motor
	 *
	 * @throws IOException when executing command fails
	 * @throws InterruptedException when the thread is interrupted, ocupied, sleeping during the activity
	 */
	public int getDefaultSpeed() throws IOException, InterruptedException {
		return Integer.parseInt(spikeCommandExecutor.execute(format("motor%s.get_default_speed()", motorEnum.asString)));
	}

	/**
	 * EVENTS
	 */

	/**
	 * Event that indicates if motor was interrupted
	 *
	 * @return whether or not the motor was interrupted
	 *
	 * @throws IOException when executing command fails
	 * @throws InterruptedException when execution is interrupted
	 */
	public boolean wasInterrupted() throws IOException, InterruptedException {
		return Boolean.parseBoolean(spikeCommandExecutor.execute(format("evaluator(\"motor%s.was_interrupted()\")", motorEnum.asString)));
	}

	/**
	 * Gets whether or not the motor was stalled
	 *
	 * @return wheter or not te motor was stalled
	 *
	 * @throws IOException when executing command fails
	 * @throws InterruptedException when the thread is interrupted, ocupied, sleeping during the activity
	 */
	public boolean wasStalled() throws IOException, InterruptedException {
		return Boolean.parseBoolean(spikeCommandExecutor.execute(format("evaluator(\"motor%s.was_stalled()\")", motorEnum.asString)));
	}

	/**
	 * Settings
	 */

	/**
	 * Sets the degrees counted.
	 *
	 * @param degreesCounted the degrees counted
	 *
	 * @throws IOException when executing the command fails
	 */
	public void setDegreesCounted(final int degreesCounted) throws IOException {
		spikeCommandExecutor.executeVoid(format("motor%s.set_degrees_counted(" + degreesCounted + ")", motorEnum.asString));
	}

	/**
	 * Sets the default speed of the motor
	 *
	 * @param defaultSpeed the default speed (0-100)
	 *
	 * @throws IOException when executing the command fails
	 */
	public void setDefaultSpeed(final int defaultSpeed) throws IOException {
		spikeCommandExecutor.executeVoid(format("motor%s.set_default_speed(" + defaultSpeed + ")", motorEnum.asString));
	}

	/**
	 * Sets the default behaviour when a motor stops
	 *
	 * @param stopAction the stop action (coast, brake, hold). Default is coast
	 *
	 * @throws IOException when executing the command fails
	 */
	public void setStopAction(final String stopAction) throws IOException {
		spikeCommandExecutor.executeVoid(format("motor%s.set_stop_action(" + stopAction + ")", motorEnum.asString));
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}