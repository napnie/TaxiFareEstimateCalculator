package com.napnie.tfec;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Request Map data from Google Map Direction API.
 * @author Nitith Chayakul
 *
 */
public class MapData {
	/** Request answer in JSON. */
	private static JsonObject result ;

	/** Private Constructor.
	 *  Not allow to create this object.
	 */
	private MapData() {}
	
	/**
	 * Generate Route from origin and destination.
	 * If there is a error in request then route object will only contain status 
	 * with that said error.
	 * @param origin - origin point of route
	 * @param destination - destination point of route
	 * @return Route of this two point 
	 * ,if request error then only status variable will be initialized.
	 */
	public static Route generateRoute(String origin, String destination) {
		String url = getDirectionRequest(origin, destination);
		BufferedReader json = getJSON( url );
		
		result = new JsonParser().parse( json ).getAsJsonObject();
		
		String status = result.get("status").getAsString();
		if( !status.equals("OK") ) return new Route(status);
		return new Route( result );
	}

	/**
	 * Get JSON file from google server.
	 * @param url - google api request
	 * @return JSON in String
	 */
	private static BufferedReader getJSON(String url) {
		InputStream mapJSON = null;
		try {
			mapJSON = new URL(url).openStream();
		} catch (MalformedURLException mf) {
			throw new RuntimeException( mf.getMessage() );
		} catch (IOException io) {
			throw new RuntimeException( io.getMessage() );
		}
		BufferedReader in = new BufferedReader(new InputStreamReader(mapJSON, StandardCharsets.UTF_8));
		return in;
	}
	
	/**
	 * Read api key.
	 * @return api key
	 */
	private static String readAPIKey() {
		return PropertiesUtil.getAPIKey();
	}

	/**
	 * Generate Google Maps Directions API request.
	 * @param origin - Origin point
	 * @param destination - Destination point
	 * @return API request to Google Map Direction
	 */
	private static String getDirectionRequest(String origin, String destination) {
		String link = "https://maps.googleapis.com/maps/api/directions/json?origin="
				+ formalize(origin) + "&destination=" + formalize(destination)
				+ "&language=en&region=th&departure_time=now&key=" + readAPIKey();
		if( link.length() > 8192 ) throw new IllegalArgumentException();
		return link;
	}

	/**
	 * Formalize string so that it fit API request requirement.
	 * @param string - String that want to formalize
	 * @return formalized String
	 */
	private static String formalize(String string) {
		return formalize(string, 0);
	}

	/**
	 * Helper method for formalize String.
	 * @param string
	 * @param index
	 * @return
	 */
	private static String formalize(String string, int index) {
		if( index >= string.length() ) return string;
		char check = string.charAt(index);
		if( !Character.isLetter(check) ) {
			int lastNonChar = index;
			while( !Character.isLetter( string.charAt(lastNonChar) ) ) lastNonChar++;
			string = string.substring(0,index) + "+" + string.substring(lastNonChar, string.length());
		}
		return formalize(string, index+1);
	}

}
