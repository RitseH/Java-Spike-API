package ritse.spike;

import static org.easymock.EasyMock.expect;

import java.awt.*;
import java.io.IOException;

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

	private final int brightness_light_1 = 100;
	private final int brightness_light_2 = 50;
	private final int brightness_light_3 = 0;

	private final int brightness = 70;

	private final String redColor = "red";


	@BeforeEach
	public void setUp() {
		replayAll();
		colorSensor = new ColorSensor(spikeCommandExecutor);
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
}
