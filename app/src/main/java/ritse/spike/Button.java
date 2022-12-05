package ritse.spike;

import static java.lang.Integer.parseInt;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

import java.io.IOException;
import java.util.function.Consumer;

import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Class that represents a button on the mindstorms hub
 */
public class Button {

	/**
	 * The button enum
	 */
	private final ButtonEnum buttonEnum;

	/**
	 * The spike command executor
	 */
	private final SpikeCommandExecutor spikeCommandExecutor;

	/**
	 * The desired value that is returned when pressing a button.
	 */
	private String desiredValue;


	/**
	 * The constructor.
	 *
	 * @param buttonEnum the button enum
	 * @param executor the command executor
	 */
	public Button(final ButtonEnum buttonEnum,final SpikeCommandExecutor executor) {
		this.buttonEnum = buttonEnum;
		this.spikeCommandExecutor = executor;
	}

	/**
	 * Method for adding callback on mindstorms button
	 *
	 * @param callback the callback value
	 *
	 * @throws IOException when executing command fails
	 * @throws InterruptedException when the thread is interrupted, ocupied, sleeping during the activity
	 */
	public void callback(final Consumer<Integer> callback) throws IOException, InterruptedException {
		spikeCommandExecutor.addCallback(String.format("hub.button.%s.callback", buttonEnum.asString), args -> {
			callback.accept(parseInt(args));
		});

	}


	@Override
	public String toString() {
		return reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public String getDesiredValue() {
		return desiredValue;
	}

	public void setDesiredValue(String desiredValue) {
		this.desiredValue = desiredValue;
	}
}