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
public class DistanceSensorTest extends EasyMockSupport {

	@Mock
	private SpikeCommandExecutor spikeCommandExecutor;

	private DistanceSensor distanceSensor;

	private final int right_top = 10;
	private final int right_bottom = 20;
	private final int left_top = 30;
	private final int left_bottom = 40;

	private final int brightness = 100;

	@BeforeEach
	public void setUp() {
		replayAll();
		distanceSensor = new DistanceSensor(spikeCommandExecutor);
		verifyAll();
		resetAll();
	}

	@Test
	public void testGetDistanceCm() throws IOException, InterruptedException {
		expect(spikeCommandExecutor.execute(String.format("distance_sensor.get_distance_cm()"))).andReturn("50");
		replayAll();

		final int distanceCm = distanceSensor.getDistanceCm();

		verifyAll();
		Assertions.assertEquals(50, distanceCm);
	}

	@Test
	public void testGetDistanceInches() throws IOException, InterruptedException {
		expect(spikeCommandExecutor.execute(String.format("distance_sensor.get_distance_inches()"))).andReturn("35");
		replayAll();

		final int distanceInches = distanceSensor.getDistanceInches();

		verifyAll();
		Assertions.assertEquals(35, distanceInches);
	}

	@Test
	public void testGetDistancePercentage() throws IOException, InterruptedException {
		expect(spikeCommandExecutor.execute(String.format("distance_sensor.get_distance_percentage()"))).andReturn("25");
		replayAll();

		final int distancePercentage = distanceSensor.getDistancePercentage();

		verifyAll();
		Assertions.assertEquals(25, distancePercentage);
	}

	@Test
	public void testWaitForDistanceFartherThan() throws IOException, InterruptedException {
		//todo: maken
	}

	@Test
	public void testWaitForDistanceCloserThan() throws IOException, InterruptedException {
		//todo: maken
	}

	@Test
	public void testLightUpAll() throws IOException, InterruptedException {
		spikeCommandExecutor.executeVoid(String.format("distance_sensor.light_up_all(%d)", brightness));
		replayAll();

		distanceSensor.lightUpAll(brightness);

		verifyAll();
	}

	@Test
	public void testLightUpAllNoParam() throws IOException, InterruptedException {
		spikeCommandExecutor.executeVoid(String.format("distance_sensor.light_up_all()"));
		replayAll();

		distanceSensor.lightUpAll();

		verifyAll();
	}

	@Test
	public void testLightUp() throws IOException, InterruptedException {
		spikeCommandExecutor.executeVoid(String.format("distance_sensor.light_up(%d, %d, %d, %d)", right_top, right_bottom, left_bottom, left_top));
		replayAll();

		distanceSensor.lightUp(right_top, right_bottom, left_bottom, left_top);

		verifyAll();
	}
}
