package com.napnie.tfec;

import org.jsoup.Jsoup;

import com.google.gson.JsonObject;

/**
 * Detail of Step from Google Map API.
 * @author Nitith Chayakul
 *
 */
public class Step {
	/** Distance of this step. */
	private int distance;
	/** Duration of this step. */
	private int duration;
	/** Step's instruction. */
	private String instruction;
	/** Step's polyline. */
	private String polyline;
	/** Step's trevel mode. */
	private String travelMode;
	/** Step start point's location in latitude and longitude format. */
	private String location;
	
	/**
	 * Initialize Step.
	 * @param step - JsonObject create from JSON from Google Map Direction API
	 */
	public Step(JsonObject step) {
		initAttributes(step);
	}
	
	/**
	 * Extract attributes from JsonObject.
	 * @param step - JsonObject create from JSON from Google Map Direction API
	 */
	private void initAttributes(JsonObject step) {
		distance = step.getAsJsonObject("distance").get("value").getAsInt();
		duration = step.getAsJsonObject("duration").get("value").getAsInt();
		
		instruction = step.getAsJsonPrimitive("html_instructions").toString();
		
		polyline = step.getAsJsonObject("polyline").get("points").getAsString();
		travelMode = step.get("travel_mode").getAsString();
		
		String lat = step.getAsJsonObject("start_location").get("lat").getAsString();
		String lng = step.getAsJsonObject("start_location").get("lng").getAsString();
		location = lat + "," + lng ;
	}
	
	public String getLocation() { return location; }
	
	public int getDistance() { return distance; }
	
	public int getDuration() { return duration; }
	
	/** Get instruction with html tag intact. */
	public String getHTMLInstruction() { return instruction.substring(1, instruction.length()-1 ); }
	
	/** Get instruction without html tag. */
	public String getInstruction() { 
		return Jsoup.parse(instruction.substring(1, instruction.length()-1 ) ).body().text(); 
	}
	
	public String getPolyline() { return polyline; }
	
	public String getTravelMode() { return travelMode; }

}
