package ritse.spike;

import static org.easymock.EasyMock.anyLong;
import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.expect;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static ritse.spike.MotorEnum.A;
import static ritse.spike.MotorEnum.B;
import static ritse.spike.MotorEnum.E;
import static ritse.spike.MotorEnum.F;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import org.easymock.Capture;
import org.easymock.EasyMockExtension;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(EasyMockExtension.class)
public class MindstormsHubTest extends EasyMockSupport {

	@Mock
	private SpikeCommandExecutor spikeCommandExecutor;

	@Mock
	private ScheduledExecutorService executorService;

	private Capture<Runnable> capture;
	private MindstormsHubImpl hub;

	private Map<MotorEnum, Motor> motorMap;
	private Map<ButtonEnum, Button> buttonMap;

	private final String portChar = "C";

	private final String text = "displayText";

	@Mock
	private ScheduledFuture scheduledFuture;

	@BeforeEach
	public void setUp() {
		replayAll();
		hub = new MindstormsHubImpl(spikeCommandExecutor);
		motorMap = new HashMap<>();
		buttonMap = new HashMap<>();
		verifyAll();
		resetAll();
	}

	@Test
	public void getColorSensorTest () throws IOException {
		hub.createColorSensor(portChar);
		assertNotNull(hub.getColorSensor());
	}

	@Test
	public void getGetDistanceSensorTest () throws IOException {
		hub.createDistanceSensor(portChar);
		assertNotNull(hub.getDistanceSensor());
	}

	@Test
	public void createMotorTest() throws IOException {
		// arrange
		spikeCommandExecutor.executeVoid("motorA = Motor('A')");
		replayAll();

		// act
		hub.createMotor(A);
		final Motor motor = hub.getMotorByMotorEnum(A);

		// assert
		assertEquals(new Motor(A, spikeCommandExecutor), motor);

		verifyAll();
	}

	@Test
	@Disabled
	public void testInitialize() throws IOException, InterruptedException {
		// arrange
		spikeCommandExecutor.executeVoid("\003");
		spikeCommandExecutor.executeVoid("from spike import PrimeHub, LightMatrix, Button, StatusLight, MotionSensor, Speaker, ColorSensor, App, DistanceSensor, Motor");
		spikeCommandExecutor.executeVoid("import hub");
		spikeCommandExecutor.executeVoid("primeHub = PrimeHub()");

		expect(spikeCommandExecutor.execute("color_sensor.get_color()")).andReturn("Red");
		expect(executorService.scheduleAtFixedRate(capture(capture), anyLong(), anyLong(), anyObject())).andReturn(scheduledFuture);


		buttonMap.put(ButtonEnum.LEFT,new Button(ButtonEnum.LEFT, spikeCommandExecutor));
		buttonMap.put(ButtonEnum.RIGHT, new Button(ButtonEnum.RIGHT, spikeCommandExecutor));
		buttonMap.put(ButtonEnum.CENTER, new Button(ButtonEnum.CENTER, spikeCommandExecutor));
		hub.createMotor(A);
		hub.createMotor(B);
		hub.createMotor(E);
		hub.createMotor(F);
		hub.createColorSensor("C");
		hub.createDistanceSensor("D");
		hub.initializeEvalFunction();
		replayAll();

		// act
		hub.initialize();

		assertNotNull(hub.getButtonByEnum(ButtonEnum.LEFT));
		assertNotNull(hub.getButtonByEnum(ButtonEnum.RIGHT));
		assertNotNull(hub.getButtonByEnum(ButtonEnum.CENTER));

		assertNotNull(hub.getMotorByMotorEnum(A));
		assertNotNull(hub.getMotorByMotorEnum(B));
		assertNotNull(hub.getMotorByMotorEnum(E));
		assertNotNull(hub.getMotorByMotorEnum(F));

		assertNotNull(hub.getDistanceSensor());

		assertNotNull(hub.getColorSensor());
		verifyAll();
	}

	@Test
	public void testDisplayText() throws IOException {
		// arrange
		spikeCommandExecutor.executeVoid(String.format("hub.display.show(\"%s\")", text));
		replayAll();

		// act
		hub.displayText(text);
		verifyAll();
	}

	@Test
	public void testCreateDistanceSensor() throws IOException {
		// arrange
		spikeCommandExecutor.executeVoid(String.format("distance_sensor = DistanceSensor('%s')", portChar));
		replayAll();

		// act
		hub.createDistanceSensor(portChar);
		verifyAll();
	}

	@Test
	public void testCreateColorSensor() throws IOException {
		// arrange
		spikeCommandExecutor.executeVoid(String.format("color_sensor = ColorSensor('%s')", portChar));
		replayAll();

		// act
		hub.createColorSensor(portChar);
		verifyAll();
	}

}
