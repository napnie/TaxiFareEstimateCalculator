package com.napnie.tfec;

import com.google.gson.JsonObject;

import net.htmlparser.jericho.Renderer;
import net.htmlparser.jericho.Segment;
import net.htmlparser.jericho.Source;

public class Step {
	
	private int distance;
	private int duration;
	private String instruction;
	private String polyline;
	private String travelMode;
	
	public Step(JsonObject step) {
		distance = step.getAsJsonObject("distance").get("value").getAsInt();
		duration = step.getAsJsonObject("duration").get("value").getAsInt();
		
		instruction = step.getAsJsonPrimitive("html_instructions").toString();
		instruction = formalize(instruction);
		
		polyline = step.getAsJsonObject("polyline").get("points").getAsString();
		travelMode = step.get("travel_mode").getAsString();
	}
	
	private String formalize(String instruction) {
		instruction = instruction.substring(1, instruction.length()-1 );
		Source htmlSource = new Source(instruction);
	    Segment htmlSeg = new Segment(htmlSource, 0, htmlSource.length());
	    Renderer htmlRend = new Renderer(htmlSeg);
	    return htmlRend.toString();
	}
	
	public int getDistance() { return distance; }
	
	public int getDuration() { return duration; }
	
	public String getInstruction() { return instruction; }
	
	public String getPolyline() { return polyline; }
	
	public String getTravelMode() { return travelMode; }

}
