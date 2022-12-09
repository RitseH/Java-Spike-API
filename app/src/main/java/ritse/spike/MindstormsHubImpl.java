package ritse.spike;

import static java.lang.String.format;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MindstormsHubImpl implements MindstormsHub {

	private static final Logger LOGGER = Logger.getLogger(MindstormsHubImpl.class.getName());
	/**
	 * The command executor of the mindstorms hub
	 */
	private final SpikeCommandExecutor spikeCommandExecutor;
	/**
	 * The map which contains the motors of a mindstorms hub combined to the port
	 */
	private final Map<MotorEnum, Motor> motorMap = new HashMap<>();
	/**
	 * The map which contains the buttons of a mindstorms hub
	 */
	private final Map<ButtonEnum, Button> buttonMap = new HashMap<>();
	/**
	 * The distance sensor of the mindstorms hub
	 */
	private DistanceSensor distanceSensor;
	;
	/**
	 * The color sensor of the mindstorms hub
	 */
	private ColorSensor colorSensor;
	;

	/**
	 * Constructor.
	 *
	 * @param port the port
	 */
	public MindstormsHubImpl(final String port) throws IOException {
		this(new SpikeCommandExecutorImpl(port));
	}

	/**
	 * Test constructor
	 *
	 * @param executor the executor
	 */
	protected MindstormsHubImpl(final SpikeCommandExecutor executor) {
		this.spikeCommandExecutor = executor;

	}

	@Override
	public void initialize() throws IOException {
		LOGGER.log(Level.INFO, "initializing mindstorms hub");
		spikeCommandExecutor.executeVoid("\003");
		spikeCommandExecutor.executeVoid("from spike import PrimeHub, LightMatrix, Button, StatusLight, MotionSensor, Speaker, ColorSensor, App, DistanceSensor, Motor");
		spikeCommandExecutor.executeVoid("import hub");
		spikeCommandExecutor.executeVoid("primeHub = PrimeHub()");

		buttonMap.put(ButtonEnum.LEFT, new Button(ButtonEnum.LEFT, spikeCommandExecutor));
		buttonMap.put(ButtonEnum.RIGHT, new Button(ButtonEnum.RIGHT, spikeCommandExecutor));
		buttonMap.put(ButtonEnum.CENTER, new Button(ButtonEnum.CENTER, spikeCommandExecutor));

		createMotor(MotorEnum.A);
		createMotor(MotorEnum.B);
		createMotor(MotorEnum.E);
		createMotor(MotorEnum.F);

		createColorSensor("C");
		createDistanceSensor("D");
		initializeEvalFunction();
	}

	@Override
	public void displayText(final String text) throws IOException {
		spikeCommandExecutor.executeVoid(format("hub.display.show(\"%s\")", text));
	}

	@Override
	public void displayImage(String text) throws IOException {
		try {
			spikeCommandExecutor.execute(format("hub.display.show(%s)", text));
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void createMotor(final MotorEnum motorEnum) throws IOException {
		spikeCommandExecutor.executeVoid(format("motor%s = Motor('" + motorEnum.asString + "')", motorEnum.asString));
		handleAddMotor(motorEnum);
	}

	@Override
	public Motor getMotorByMotorEnum(final MotorEnum motorEnum) {
		return motorMap.get(motorEnum);
	}

	@Override
	public void createDistanceSensor(final String portChar) throws IOException {
		distanceSensor = new DistanceSensor(spikeCommandExecutor);
		spikeCommandExecutor.executeVoid(format("distance_sensor = DistanceSensor('%s')", portChar));
	}

	@Override
	public DistanceSensor getDistanceSensor() {
		return distanceSensor;
	}

	@Override
	public void createColorSensor(final String portChar) throws IOException {
		colorSensor = new ColorSensor(spikeCommandExecutor);
		spikeCommandExecutor.executeVoid(format("color_sensor = ColorSensor('%s')", portChar));
	}

	@Override
	public ColorSensor getColorSensor() {
		return colorSensor;
	}


	@Override
	public Button getButtonByEnum(final ButtonEnum buttonEnum) {
		return buttonMap.get(buttonEnum);
	}

	@Override
	public void handleAddMotor(final MotorEnum motorEnum) {
		motorMap.put(motorEnum, new Motor(motorEnum, spikeCommandExecutor));
	}

	@Override
	public void initializeEvalFunction() throws IOException {
		spikeCommandExecutor.executeVoid("def evaluator(msgType, counter, fn):\n " +
				"return \"!{}:{}:{}%\".format(msgType, counter, eval(fn))\r\n");
	}
}




