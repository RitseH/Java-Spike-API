package ritse.spike;

import static org.easymock.EasyMock.anyLong;
import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.newCapture;

import java.io.IOException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import org.easymock.Capture;
import org.easymock.EasyMockExtension;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(EasyMockExtension.class)
public class ColorSensorTest extends EasyMockSupport {

	@Mock
	private SpikeCommandExecutor spikeCommandExecutor;

	private ColorSensor colorSensor;

	@Mock
	private ScheduledExecutorService executorService;
	private final int brightness_light_1 = 100;
	private final int brightness_light_2 = 50;
	private final int brightness_light_3 = 0;

	private final int brightness = 70;

	private final String redColor = "red";

	private Capture<Runnable> capture;

	@Mock
	private ScheduledFuture scheduledFuture;


	@BeforeEach
	public void setUp() {
		capture = newCapture();
		expect(executorService.scheduleAtFixedRate(capture(capture), anyLong(), anyLong(), anyObject())).andReturn(scheduledFuture);
		replayAll();
		colorSensor = new ColorSensor(spikeCommandExecutor, executorService);
		verifyAll();
		resetAll();
	}

	@Test
	public void testGetReflectedLight() throws IOException, InterruptedException {
		expect(spikeCommandExecutor.execute(String.format("color_sensor.get_reflected_light()"))).andReturn("90");
		replayAll();

		final int reflectedLight = colorSensor.getReflectedLight();

		verifyAll();
		Assertions.assertEquals(90, reflectedLight);
	}

	@Test
	public void testGetAmbientLight() throws IOException, InterruptedException {
		expect(spikeCommandExecutor.execute(String.format("color_sensor.get_ambient_light()"))).andReturn("15");
		replayAll();

		final int ambientLight = colorSensor.getAmbientLight();

		verifyAll();
		Assertions.assertEquals(15, ambientLight);
	}

	@Test
	public void testGetColor() throws IOException, InterruptedException {
		expect(spikeCommandExecutor.execute(String.format("color_sensor.get_color()"))).andReturn("red");
		replayAll();

		final String color = colorSensor.getColor();

		verifyAll();
		Assertions.assertEquals(redColor, color);
	}

	@Test
	public void testWaitUntilColor() throws IOException {
		// todo: maak test
	}

	@Test
	public void testForNewColor() throws IOException {
		// todo: maak test
	}

	@Test
	public void testLightUp() throws IOException {
		spikeCommandExecutor.executeVoid(String.format("color_sensor.light_up(%d, %d, %d)", brightness_light_1, brightness_light_2, brightness_light_3));
		replayAll();

		colorSensor.lightUp(brightness_light_1, brightness_light_2, brightness_light_3);
		verifyAll();
	}

	@Test
	public void testLightUpAll() throws IOException {
		spikeCommandExecutor.executeVoid(String.format("color_sensor.light_up_all(" + brightness + ")"));
		replayAll();

		colorSensor.lightUpAll(brightness);
		verifyAll();
	}

	@Test
	public void testColorSensorIsDesiredValue () throws IOException, InterruptedException {
		// Arrange
		expect(spikeCommandExecutor.execute("color_sensor.get_color()")).andReturn("Green");
		replayAll();

		// Act
		capture.getValue().run();

		// Assert
		verifyAll();
	}

	@Test
	public void testColorSensorIsNotDesiredValue () throws IOException, InterruptedException {
		// Arrange
		expect(spikeCommandExecutor.execute("color_sensor.get_color()")).andReturn("PURPLE");
		replayAll();

		// Act
		capture.getValue().run();

		// Assert
		verifyAll();
	}
}
