package service;

public class IDUtil {

/*
 * Deze functie genereert een uniek ID en geeft hem terug.
 */
	public long getNextId() {
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
		}
		return System.currentTimeMillis() - 1557429970258L;
	}

}