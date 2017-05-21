package com.napnie.tfec;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class Route implements Iterable<Step> {
	private List<Step> route;
	
	private int distance;
	private int duration;
	private int trafficDuration;
	private String origin;
	private String destination;
	private String overview_polyline;
	private String status;
	
	private String originLocation;
	private String destinationLocation;

	public Route(JsonObject result) {
		initAttributes(result);
	}
	
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
	
	private String formalize(String polyline) {
		return polyline.substring(1, polyline.length()-1 );
	}
	
	private void initStep(JsonArray steps) {
		route = new ArrayList<Step>();
		for(int i = 0 ; i<steps.size() ; i++) {
			JsonObject step = steps.get(i).getAsJsonObject();
			Step thisStep = new Step(step);
			route.add(thisStep);
		}
	}
	
	public String getEmbledMapRequest() {
		return "https://www.google.com/maps/embed/v1/directions?origin="
						+ originLocation + "&destination=" + destinationLocation + "&key=";
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
	
	public List<Step> getSteps() { return route; }

}
