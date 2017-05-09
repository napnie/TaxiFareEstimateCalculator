package tfec;

/**
 * Calculate Taxi Fare from Google Maps Directions API.
 * @author Nitith Chayakul
 *
 */
public class FareCalculator {
	/** Fare rate at the beginning. */
	private double startFare;
	/** Fare rate when running in km */
	private double runFare;
	/** Fare rate when waiting for hour. */
	private double waitFare;
	
	private double distance;
	private double duration;
	private double waittime;
	
	/**
	 * Initialize FareCalculator with default argument.
	 * Start Fare = 35
	 * Run Fare = 6 /km
	 * Wait Fare = 120 /hour
	 */
	public FareCalculator() {
		this(35, 6, 120);
	}
	
	/** Initialize FareCalculator. */
	public FareCalculator(double startFare, double runFare, double waitFare) {
		setStartFare(startFare);
		setRunFare(runFare);
		setWaitFare(waitFare);
		distance = 0;
		duration = 0;
		waittime = 0;
	}
	
	/**
	 * Set Start Fare.
	 * @param startFare
	 */
	public void setStartFare(double startFare) {
		this.startFare = startFare;
	}
	
	/**
	 * Set Running Fare.
	 * @param runFare
	 */
	public void setRunFare(double runFare) {
		this.runFare = runFare;
	}
	
	/**
	 * Set Wait Fare.
	 * @param waitFare
	 */
	public void setWaitFare(double waitFare) {
		this.waitFare = waitFare;
	}
	
	/**
	 * Calculate taxi fare.
	 * @param distance - distance between origin and destination in km.
	 * @param waitTime - wait time in hour.
	 * @return estimated fare of taxi.
	 */
	public double estimateFare(double distance, double waitTime) {
		return startFare + (runFare * distance) + (waitTime * waitFare);
	}
}
