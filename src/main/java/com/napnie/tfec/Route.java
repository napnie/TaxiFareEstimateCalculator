package com.napnie.tfec;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Route detail.
 * Only create from Google Map Direction API Request answer.
 * @author Nitith Chayakul
 *
 */
public class Route implements Iterable<Step> {
	/** List of every steps in this route. */
	private List<Step> route;
	
	/** Total distance of this route. */
	private int distance;
	/** Total duration of this route. */
	private int duration;
	/** Increase duration becuase of traffic in this route. */
	private int trafficDuration;
	/** Origin point's name  */
	private String origin;
	/** Destination point's name */
	private String destination;
	/** Overview polyline of this route. Only usable with Google Map API. */
	private String overview_polyline;
	/** Request status of Google Map Direction API. 
	 * If status is anything other than "OK", this will be only initialized variable of Route Object.
	 */
	private String status;
	
	/** Origin point's location in "Latitude,Longitude" format. */
	private String originLocation;
	/** Destination point's location in "Latitude,Longitude" format. */
	private String destinationLocation;

	/**
	 * Initialize complete Route object.
	 * @param result - JsonObject of Google Map Direction API Request answer.
	 */
	public Route(JsonObject result) {
		initAttributes(result);
	}
	
	/**
	 * Initialize Route that only Initialize status variable.
	 * @param status - status of Google Map Direction API Request answer
	 */
	public Route(String status) { this.status = status; }
	
	/**
	 * Extract attributes from Google Map Direction API Request answer.
	 * @param result - Google Map Direction API Request answer
	 */
	private void initAttributes(JsonObject result) {
		status = result.getAsJsonPrimitive("status").getAsString();
		JsonArray routeArray = result.getAsJsonArray("routes");
		JsonObject innerRoute = routeArray.get(0).getAsJsonObject();
		overview_polyline = formalize( innerRoute.getAsJsonObject("overview_polyline").getAsJsonPrimitive("points").toString() );
		JsonObject innerLegs = innerRoute.getAsJsonArray("legs").get(0).getAsJsonObject();
		distance = innerLegs.getAsJsonObject("distance").get("value").getAsInt();
		duration = innerLegs.getAsJsonObject("duration").get("value").getAsInt();
		
		String oriLat = innerLegs.getAsJsonObject("start_location").get("lat").getAsString();
		String oriLng = innerLegs.getAsJsonObject("start_location").get("lng").getAsString();
		String desLat = innerLegs.getAsJsonObject("end_location").get("lat").getAsString();
		String desLng = innerLegs.getAsJsonObject("end_location").get("lng").getAsString();
		originLocation = oriLat + "," + oriLng;
		destinationLocation = desLat + "," + desLng;
		
		trafficDuration = innerLegs.getAsJsonObject("duration_in_traffic").get("value").getAsInt();
		origin = innerLegs.get("start_address").getAsString();
		destination = innerLegs.get("end_address").getAsString();
		initStep( innerLegs.getAsJsonArray("steps") );
	}
	
	/**
	 * Formalize polyline string.
	 * @param polyline - polyline that want to formalize.
	 * @return formalized polyline
	 */
	private String formalize(String polyline) {
		return polyline.substring(1, polyline.length()-1 );
	}
	
	/**
	 * Create list of steps in this route.
	 * @param steps - JsonArray of steps
	 */
	private void initStep(JsonArray steps) {
		route = new ArrayList<Step>();
		for(int i = 0 ; i<steps.size() ; i++) {
			JsonObject step = steps.get(i).getAsJsonObject();
			Step thisStep = new Step(step);
			route.add(thisStep);
		}
	}
	
	public String getDestinationLocation() { return destinationLocation; }
	
	public String getOriginLocation() { return originLocation; }
	
	public String getStatus() { return status; }

	public int getDistance() { return distance; }
	
	public int getDuration() { return Math.min(trafficDuration, duration); }
	
	public int getWaitTime() { 
		if( trafficDuration > duration ) return trafficDuration - duration;
		return 0;
	}
	
	public String getOrigin() { return origin; }
	
	public String getDestination() { return destination; }
	
	public String getOverviewPolyline() { return overview_polyline; }
	
	/** Make Route Iterable. */
	public Iterator<Step> iterator() {
		return route.iterator();
	}

}
