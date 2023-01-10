package ritse.spike;

import static org.easymock.EasyMock.expect;

import java.io.IOException;

import org.easymock.EasyMockExtension;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(EasyMockExtension.class)
public class MotorTest extends EasyMockSupport {

	@Mock
	private SpikeCommandExecutor spikeCommandExecutor;

	private Motor motor;

	private final int speed = 100;

	private final String stopAction = "brake";

	private final int power = 50;
	private final int seconds = 5;

	private final int degrees = 40;

	private final int position = 60;

	private final float rotations = 2;

	@BeforeEach
	public void setUp() {
		replayAll();
		motor = new Motor(MotorEnum.A, spikeCommandExecutor);
		verifyAll();
		resetAll();
	}

	@Test
	public void testWasStalled() throws IOException, InterruptedException {
		expect(spikeCommandExecutor.execute(String.format("evaluator(\"motor%s.was_stalled()\")", MotorEnum.A.asString))).andReturn("True");
		replayAll();

		final boolean wasStalled = motor.wasStalled();

		verifyAll();
		Assertions.assertEquals(true, wasStalled);
	}

	@Test
	public void testWasInterupted() throws IOException, InterruptedException {
		expect(spikeCommandExecutor.execute(String.format("evaluator(\"motor%s.was_interrupted()\")", MotorEnum.A.asString))).andReturn("True");
		replayAll();

		final boolean wasInterupted = motor.wasInterrupted();

		verifyAll();
		Assertions.assertEquals(true, wasInterupted);
	}

	@Test
	public void testSetStopAction() throws IOException {
		spikeCommandExecutor.executeVoid(String.format("motor%s.set_stop_action(" + stopAction + ")", MotorEnum.A.asString));
		replayAll();

		motor.setStopAction(stopAction);

		verifyAll();
	}

	@Test
	public void testSetDefaultSpeed() throws IOException {
		spikeCommandExecutor.executeVoid(String.format("motor%s.set_default_speed(" + speed + ")", MotorEnum.A.asString));
		replayAll();

		motor.setDefaultSpeed(speed);

		verifyAll();
	}

	@Test
	public void testSetDegreesCounted() throws IOException {
		spikeCommandExecutor.executeVoid(String.format("motor%s.set_degrees_counted(" + degrees + ")", MotorEnum.A.asString));
		replayAll();

		motor.setDegreesCounted(degrees);

		verifyAll();
	}


	@Test
	public void testStop() throws IOException {
		spikeCommandExecutor.executeVoid(String.format("motor%s.stop()", MotorEnum.A.asString));
		replayAll();

		motor.stop();

		verifyAll();
	}

	@Test
	public void testStartAtPower() throws IOException {
		spikeCommandExecutor.executeVoid(String.format("motor%s.start_at_power(" + power + ")", MotorEnum.A.asString));
		replayAll();

		motor.startAtPower(power);

		verifyAll();
	}

	@Test
	public void testStartWithoutSpeed() throws IOException {
		spikeCommandExecutor.executeVoid(String.format("motor%s.start()", MotorEnum.A.asString));
		replayAll();

		motor.start();

		verifyAll();
	}

	@Test
	public void testStartWithSpeed() throws IOException {
		spikeCommandExecutor.executeVoid(String.format("motor%s.start(" + speed + ")", MotorEnum.A.asString));
		replayAll();

		motor.start(speed);

		verifyAll();
	}

	@Test
	public void testRunForRotations() throws IOException {
		spikeCommandExecutor.executeVoid(String.format("motor%s.run_for_rotations(" + rotations + ")", MotorEnum.A.asString));
		replayAll();

		motor.runForRotations(rotations);

		verifyAll();
	}

	@Test
	public void testRunToDegreesCounted() throws IOException {
		spikeCommandExecutor.executeVoid(String.format("motor%s.run_to_degrees_counted(" + degrees + ")", MotorEnum.A.asString));
		replayAll();

		motor.runToDegreesCounted(degrees);

		verifyAll();
	}

	@Test
	public void testRunToPosition() throws IOException, InterruptedException {
		spikeCommandExecutor.executeVoid(String.format("motor%s.run_to_position(" + position + ")", MotorEnum.A.asString));
		replayAll();

		motor.runToPosition(position);

		verifyAll();
	}

	@Test
	public void testRunForDegrees() throws IOException {
		spikeCommandExecutor.executeVoid(String.format("motor%s.run_for_degrees(" + degrees + ")", MotorEnum.A.asString));
		replayAll();

		motor.runForDegrees(degrees);

		verifyAll();
	}

	@Test
	public void testRunForSeconds() throws IOException {
		spikeCommandExecutor.executeVoid(String.format("motor%s.run_for_seconds(" + seconds + ")", MotorEnum.A.asString));
		replayAll();

		motor.runForSeconds(seconds);

		verifyAll();
	}

	@Test
	public void testGetSpeed() throws IOException, InterruptedException {
		// arrange
		expect(spikeCommandExecutor.execute(String.format("motor%s.get_speed()", MotorEnum.A.asString))).andReturn("100");
		replayAll();
		// act
		final int speed = motor.getSpeed();

		// assert
		Assertions.assertEquals(100, speed);
		verifyAll();
	}

	@Test
	public void testGetPosition() throws IOException, InterruptedException {
		// arrange
		expect(spikeCommandExecutor.execute(String.format("motor%s.get_position()", MotorEnum.A.asString))).andReturn("12");
		replayAll();
		// act
		final int position = motor.getPosition();

		// assert
		Assertions.assertEquals(12, position);
		verifyAll();
	}

	@Test
	public void testGetDegreesCounted() throws IOException, InterruptedException {
		// arrange
		expect(spikeCommandExecutor.execute(String.format("motor%s.get_degrees_counted()", MotorEnum.A.asString))).andReturn("49");
		replayAll();
		// act
		final int degreesCounted = motor.getDegreesCounted();

		// assert
		Assertions.assertEquals(49, degreesCounted);
		verifyAll();
	}

	@Test
	public void testGetDefaultSpeed() throws IOException, InterruptedException {
		// arrange
		expect(spikeCommandExecutor.execute(String.format("motor%s.get_default_speed()", MotorEnum.A.asString))).andReturn("1");
		replayAll();
		// act
		final int defaultSpeed = motor.getDefaultSpeed();

		// assert
		Assertions.assertEquals(1, defaultSpeed);
		verifyAll();
	}
}
