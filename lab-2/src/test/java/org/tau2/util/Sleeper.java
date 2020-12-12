package org.tau2.util;

public class Sleeper {

	public static void siesta(Long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

}
