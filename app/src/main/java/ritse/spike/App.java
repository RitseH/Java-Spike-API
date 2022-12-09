package ritse.spike;

import java.io.IOException;

public class App {
	public static void main(String[] args) throws IOException {
		System.out.println("Starting");
		MindstormsHubImpl hub = new MindstormsHubImpl("COM5");
		hub.initialize();

		hub.displayImage("hub.Image.HAPPY");
		hub.displayImage("hub.Image.COW");
	}
}
