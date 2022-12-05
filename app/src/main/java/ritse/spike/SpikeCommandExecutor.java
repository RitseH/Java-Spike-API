package ritse.spike;

import java.io.IOException;
import java.util.function.Consumer;

/**
 * The interface for the spike command executor
 */
public interface SpikeCommandExecutor {

	/**
	 * extracts the result
	 * @param bytes
	 */
	void extract(final byte[] bytes);

	/**
	 * Executes command to mindstorms hub
	 *
	 * @param command the command
	 *
	 * @return te result of the command
	 *
	 * @throws IOException when executing command fails
	 * @throws InterruptedException when executing command fails
	 */
	String execute(final String command) throws IOException, InterruptedException;

	/**
	 * Executes command without getting response back
	 *
	 * @param command the command
	 *
	 * @throws IOException when executing command fails
	 */
	void executeVoid(final String command) throws IOException;

	/**
	 * Method that returns the message number
	 *
	 * @return the message number
	 */
	int getMessageNumber();

	/**
	 * Executes command to add callback
	 *
	 * @param command the command
	 * @param callback the callback
	 *
	 * @throws IOException when executing command fails
	 * @throws InterruptedException when executing command gets interrupted
	 */
	void addCallback(final String command, Consumer<String> callback) throws IOException, InterruptedException;

}
