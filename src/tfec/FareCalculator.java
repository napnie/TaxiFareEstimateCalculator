package tfec;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * Calculate Taxi Fare from Google Maps Directions API.
 * @author Nitith Chayakul
 * @since 9/05/2017
 *
 */
public class FareCalculator {
	private MapData data;
	/** Fare rate at the beginning. */
	private double startFare;
	/** Fare rate when running in km */
	private double runFare;
	/** Fare rate when waiting for hour. */
	private double waitFare;
	
	private double distance;
	private double duration;
	private double waitTime;
	
	private String hint;
	
	/**
	 * Initialize FareCalculator with default argument.
	 * Start Fare = 35
	 * Run Fare = 6 per km
	 * Wait Fare = 120 per hour
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
		waitTime = 0;
	}
	
	/**
	 * Set Start Fare.
	 * @param startFare
	 */
	public void setStartFare(double startFare) {
		this.startFare = startFare;
	}
	
	/**
	 * Set Running Fare rate per km.
	 * @param runFare
	 */
	public void setRunFare(double runFare) {
		this.runFare = runFare;
	}
	
	/**
	 * Set Wait Fare rate per hour.
	 * @param waitFare
	 */
	public void setWaitFare(double waitFare) {
		this.waitFare = waitFare;
	}
	
	/**
	 * Get distance of estimated route in meter.
	 * Will return 0 if did not call estimateRoute() first.
	 * @return distance of estimated route
	 */
	public double getDistance() {
		if( isRouteEstimated() ) return distance;
		return 0;
	}
	
	/**
	 * Get duration of estimated route in seconds.
	 * Will return 0 if did not call estimateRoute() first.
	 * @return duration of estimated route
	 */
	public double getDuration() {
		if( isRouteEstimated() ) return duration;
		return 0;
	}
	
	/**
	 * Get wait time of estimated route in seconds.
	 * Will return 0 if did not call estimateRoute() first.
	 * @return wait time of estimated route
	 */
	public double getWaitTime() {
		if( isRouteEstimated() ) return waitTime;
		return 0;
	}
	
	/**
	 * Check if route is already estimated.
	 * @return true if it is estimated
	 */
	public boolean isRouteEstimated() {
		if( !data.getDirectionStatus().equals("OK") || data == null ) return false;
		return true;
	}
	
	/**
	 * Get status of api request.
	 * @return request's status, need to call estimateRoute first or will throw NullPointerException
	 */
	public String getRequestStatus() {
		return data.getDirectionStatus();
	}
	
	/**
	 * Get api request information.
	 * @return request information, null if status is 'OK'
	 */
	public String getHint() {
		return hint;
	}
	
	/**
	 * Calculate taxi fare.
	 * Will return 0 if did not call estimateRoute() first.
	 * @return estimated fare of taxi.
	 */
	public double estimateFare() {
		final double SEC_TO_HOUR = 1/(60.0*60) ;
		final double METER_TO_KILOMETER = 1.0/1000;
		if( isRouteEstimated() ) return startFare + (runFare * (distance * METER_TO_KILOMETER)) + ( (waitTime * SEC_TO_HOUR) * waitFare);
		return 0;
	}
	
	/**
	 * Calculate taxi fare in this route.
	 * @param origin - Origin of route that want to calculate fare
	 * @param destination - Destination of route that want to calculate fare
	 */
	public void estimateRoute(String origin, String destination) {
		data = MapData.getInstance(origin, destination);
		if( isRouteEstimated() ) {
			hint = null;
			distance = data.getDistance();
			duration = data.getDuration();
			waitTime = data.getWaitTime();
		} else {
			hint = readHint( data.getDirectionStatus() );
		}
	}
	
	/**
	 * Read api request status information.
	 * @param statusCode - status of request
	 * @return request status information
	 */
	private String readHint(String statusCode) {
		FileInputStream file;
		try {
			file = new FileInputStream("src/config.properties");
		} catch (FileNotFoundException e) {
			throw new RuntimeException( e.getMessage() );
		}
		ResourceBundle config = null;
		try {
			config = new PropertyResourceBundle(file);
		} catch (IOException e) {
			throw new RuntimeException( e.getMessage() );
		}
		return config.getString(statusCode);
	}
	
}
