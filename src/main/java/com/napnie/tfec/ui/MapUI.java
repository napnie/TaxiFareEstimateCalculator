package com.napnie.tfec.ui;

import com.napnie.tfec.PropertiesUtil;

public interface MapUI {
	
	void setMap(String origin, String destination) ;
	
	default String getAPIKey() {
		return PropertiesUtil.getProperties(PropertiesUtil.SENSITIVE_DATA_BUNDLE, "api.key");
	}
	
	default String getDefualtPlace() {
    	String thai = "<html>"
    			+ "<body>"
    			+ "<iframe width=\"600\" height=\"450\" frameborder=\"0\" style=\"border:0\""
    			+ "src=\"https://www.google.com/maps/embed/v1/view?zoom=5&center=15.8700,100.9925"	
    			+ "&key=" + getAPIKey() + "\" allowfullscreen></iframe>"
    			+ "</body>"
    			+ "</html>";
  		return thai;
    }
    
	default String getRouteMap(String origin, String destination) {
		String html = 	"<html>\n"
				+ "<body>\n"
				+ "<iframe width=\"600\" height=\"450\" frameborder=\"0\" style=\"border:0\""
				+ " src=\"https://www.google.com/maps/embed/v1/directions?origin=" + origin
				+ "&destination=" + destination + "&key=" + getAPIKey() + "\" "
				+ "allowfullscreen></iframe>\n"
				+ "</body>\n"
				+ "</html>" ;
		return html;
	}

}
