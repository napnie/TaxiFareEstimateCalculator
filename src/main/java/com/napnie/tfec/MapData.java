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

public class MapData {
	
	private static JsonObject result ;

	private MapData() {}
	
	public static Route generateRoute(String origin, String destination) {
		String url = getDirectionRequest(origin, destination);
		BufferedReader json = getJSON( url );
		
//		System.out.println(url);
		
		createResult( json );
		if( !result.get("status").getAsString().equals("OK") ) return null;
		return getRoute( result );
	}
	
	private static Route getRoute(JsonObject result) {
		return new Route(result);
	}
	
	private static void createResult(BufferedReader json) {
		result = new JsonParser().parse( json ).getAsJsonObject();
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
