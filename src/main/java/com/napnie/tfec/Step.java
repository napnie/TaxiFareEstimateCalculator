package com.napnie.tfec;

import org.jsoup.Jsoup;

import com.google.gson.JsonObject;

public class Step {
	
	private int distance;
	private int duration;
	private String instruction;
	private String polyline;
	private String travelMode;
	private String location;
	
	public Step(JsonObject step) {
		initAttributes(step);
//		distance = step.getAsJsonObject("distance").get("value").getAsInt();
//		duration = step.getAsJsonObject("duration").get("value").getAsInt();
//		
//		instruction = step.getAsJsonPrimitive("html_instructions").toString();
//		instruction = formalize(instruction);
//		
//		polyline = step.getAsJsonObject("polyline").get("points").getAsString();
//		travelMode = step.get("travel_mode").getAsString();
//		
//		String lat = step.getAsJsonObject("start_location").get("lat").getAsString();
//		String lng = step.getAsJsonObject("start_location").get("lng").getAsString();
//		location = lat + "," + lng ;
	}
	
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
	
	private String formalize(String instruction) {
		instruction = instruction.substring(1, instruction.length()-1 );
		return Jsoup.parse(instruction).body().text();
//	    return instruction;
	}
	
	public String getLocation() { return location; }
	
	public int getDistance() { return distance; }
	
	public int getDuration() { return duration; }
	
	public String getHTMLInstruction() { return instruction; }
	
	public String getInstruction() { return formalize( instruction ); }
	
	public String getPolyline() { return polyline; }
	
	public String getTravelMode() { return travelMode; }

}
