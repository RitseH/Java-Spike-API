package ritse.spike;

import static java.lang.String.format;
import static java.util.logging.Level.INFO;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fazecast.jSerialComm.SerialPort;

/**
 * The command executor implementation
 */
public class SpikeCommandExecutorImpl implements SpikeCommandExecutor {

	private static final Logger LOGGER = Logger.getLogger(SpikeCommandExecutorImpl.class.getName());

	/**
	 * the pattern of a result message
	 */
	// <type>:<message number>:result
	private static final Pattern RESULT_PATTERN = Pattern.compile("^RC:(\\d+):(.*)");

	/**
	 * The pattern of a callback message
	 */
	private static final Pattern CALLBACK_PATTERN = Pattern.compile(".*CB:(\\d+):(.*)");

	/**
	 * The serial port implementation
	 */
	private final LegoSerialPort serialPort;
	/**
	 * Handler map with exchangers
	 */
	private final Map<Integer, Exchanger<String>> handlerMap = new ConcurrentHashMap<>();
	/**
	 * The call back handler map
	 */
	private final Map<Integer, Consumer<String>> callbackHandler = new ConcurrentHashMap<>();
	/**
	 * the result
	 */
	private String result = "";
	/**
	 * the message number
	 */
	private int messageNumber = 0;

	/**
	 * Constructor.
	 *
	 * @param comport the COM port
	 */
	public SpikeCommandExecutorImpl(final String comport) throws IOException {
		this.serialPort = new SerialPortImpl(comport, 115200, this::extract);
		serialPort.open();
	}

	@Override
	public void extract(final byte[] bytes) {
		String message = new String(bytes);
		String noExclamationMark = message.substring(message.lastIndexOf("!") + 1);
		String answer = noExclamationMark.substring(0, noExclamationMark.length() - 1);
		final Matcher resultMatcher = RESULT_PATTERN.matcher(answer);
		if (resultMatcher.matches()) {

			final int counter = Integer.parseInt(resultMatcher.group(1));
			final String res = resultMatcher.group(2);
			final Exchanger<String> exchanger = handlerMap.remove(counter);
			try {
				exchanger.exchange(res);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
		final String answerToMatch = answer.trim();
		final Matcher callbackMatcher = CALLBACK_PATTERN.matcher(answerToMatch);
		if (callbackMatcher.matches()) {
			final int counter = Integer.parseInt(callbackMatcher.group(1));
			final String res = callbackMatcher.group(2);
			callbackHandler.getOrDefault(counter, (s) -> {
			}).accept(res);
		}
	}

	@Override
	public String execute(final String command) throws IOException, InterruptedException {
		final int messageCounter = messageNumber++;
		try {

			final Exchanger<String> exchanger = new Exchanger<>();
			// put in map
			handlerMap.put(messageCounter, exchanger);

			final String messageToSend = format("evaluator(\"%s\", %d, \"" + command + "\")\r\n", "RC", messageCounter);
			final Optional<OutputStream> outputStream = serialPort.getOutputStream();
			if (outputStream.isPresent()) {
				outputStream.get().write(messageToSend.getBytes(StandardCharsets.UTF_8));
			} else {
				LOGGER.log(INFO, "Could not get outputStream in SpikeCommandExecutorImpl.execute()");
			}
			try {
				result = exchanger.exchange("", 3, TimeUnit.SECONDS);
			} catch (TimeoutException e) {
				e.printStackTrace();
			}

			if (result != null) {
				final Matcher matcher = RESULT_PATTERN.matcher(result);
			} else {
				LOGGER.log(INFO, "Result is null when returned from exchanger");
			}
		} finally {
			handlerMap.remove(messageCounter);
		}
		return result;

	}

	@Override
	public void executeVoid(final String command) throws IOException {
		final Optional<OutputStream> outputStream = serialPort.getOutputStream();
		if (outputStream.isPresent()) {
			outputStream.get().write((command + "\r\n").getBytes(StandardCharsets.UTF_8));
		} else {
			LOGGER.log(INFO, "Could not get outputStream in SpikeCommandExecutorImpl.executeVoid()");
		}
	}

	public int getMessageNumber() {
		return messageNumber;
	}

	@Override
	public void addCallback(final String method, final Consumer<String> callback) throws IOException, InterruptedException {
		final int nextId = callbackHandler.keySet().stream()
				.mapToInt(Integer::intValue)
				.max()
				.orElse(0) + 1;
		callbackHandler.put(nextId, callback);
		final String command = format("%s(lambda x: print(\"CB:%d:\" + str(x) + \"%s\"))\r\n", method, nextId, '%');

		executeVoid(command);
	}

	@Override
	public void close() throws IOException {
		serialPort.close();
	}
}