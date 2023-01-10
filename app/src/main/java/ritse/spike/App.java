package ritse.spike;

import java.io.IOException;

public class App {
	public static void main(String[] args) throws IOException {
		System.out.println("Starting");
		MindstormsHub hub = new MindstormsHubImpl("COM5");
		hub.open();



	}
}
