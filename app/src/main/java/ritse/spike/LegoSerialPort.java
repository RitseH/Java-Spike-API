package ritse.spike;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Optional;

/**
 * Interface used for using serial ports
 */
public interface LegoSerialPort {

	/**
	 * @return true if port is open
	 */
	boolean isPortOpen();

	/**
	 * @return the port name
	 */
	String getPortName();

	/**
	 * Opens the port
	 *
	 * @throws IOException in case of serial communication problems
	 */
	void open() throws IOException;

	/**
	 * @return the input stream
	 * @throws IOException in case of io problems
	 */
	Optional<InputStream> getInputStream() throws IOException;

	/**
	 * @return the output stream
	 * @throws IOException in case of io exceptions
	 */
	Optional<OutputStream> getOutputStream() throws IOException;

	/**
	 * Does a really close of the comport
	 *
	 * @throws IOException in case of serial communication problems
	 */
	void close() throws IOException;

	/**
	 * Returns the list of serial ports
	 *
	 * @return the list of serial ports
	 */
	static List<String> getSerialPorts() {
		return getSerialPorts();
	}
}
