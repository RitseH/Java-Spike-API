package ritse.spike;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;
import java.util.function.Consumer;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.fazecast.jSerialComm.SerialPortMessageListener;

/**
 * Class for using serial ports
 */
public class SerialPortImpl implements LegoSerialPort {

	/**
	 * The serial port
	 */
	private final SerialPort serialPort;

	/**
	 * Constructor.
	 *
	 * @param port the port
	 * @param baudrate the baudrate
	 * @param packetHandler the packet handler
	 */
	public SerialPortImpl(final String port, final int baudrate, final Consumer<byte[]> packetHandler) {
		serialPort = SerialPort.getCommPort(port);
		serialPort.setBaudRate(baudrate);

		serialPort.addDataListener(new SerialPortMessageListener() {
			@Override
			public byte[] getMessageDelimiter() {
				return "%".getBytes();
			}

			@Override
			public boolean delimiterIndicatesEndOfMessage() {
				return true;
			}

			@Override
			public int getListeningEvents() {
				return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
			}

			@Override
			public void serialEvent(final SerialPortEvent event) {
				packetHandler.accept(event.getReceivedData());
			}
		});
	}

	@Override
	public boolean isPortOpen() {
		return serialPort.isOpen();
	}

	@Override
	public String getPortName() {
		return serialPort.getSystemPortName();
	}

	@Override
	public void open() throws IOException {
		serialPort.openPort();
	}

	@Override
	public Optional<InputStream> getInputStream() {
		return Optional.of(serialPort.getInputStream());
	}

	@Override
	public Optional<OutputStream> getOutputStream() {
		return Optional.of(serialPort.getOutputStream());
	}

	@Override
	public void close() {
		serialPort.closePort();
	}

}
