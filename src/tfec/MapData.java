package tfec;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.stringtree.json.JSONReader;

/**
 * Send request to Google Maps Directions API.
 * @author Nitith Chayakul
 * @since 9/05/2017
 *
 */
public class MapData {
	/** Reader for JSON. */
	static JSONReader reader = new JSONReader();
	/** All detail from google map api. */
	Map<String,Object> directionMap;
	/** All detail concern route. */
	Map<String,Object> routeMap;
	
	Map<String,Object> distanceMap;
	Map<String,Object> distanceDetail;
	
	private static MapData data;
	
	private MapData(){}
	
	public static MapData getInstance(String origin, String destination) {
		data = new MapData();
		String directionURL = getDirectionRequest(origin, destination);
		String distanceURL = getDistanceRequest(origin, destination);
		data.setDirectionMap( initializeMap( getJSON( directionURL ) ) );
		data.initializeRouteMap();
		
		data.setDistanceMap( initializeMap( getJSON( distanceURL ) ) );
		data.initializeDistanceDetail();
		
		return data;
	}
	
	public int getWaitTime() {
		@SuppressWarnings("unchecked")
		Map<String,Object> duration = (Map<String,Object>) distanceDetail.get("duration");
		String waitTimeText = String.valueOf( duration.get("value") );
		int waitTime = Integer.parseInt(waitTimeText);
		if( getDuration() > waitTime ) waitTime = 0;
		else waitTime = waitTime - getDuration();
		return waitTime;
	}
	
	@SuppressWarnings("unchecked")
	private void initializeDistanceDetail() {
		List<Object> rows = (List<Object>) distanceMap.get("rows");
		Map<String,Object> innerRows = (Map<String,Object>) rows.get(0);
		List<Object> elements = (List<Object>) innerRows.get("elements");
		Map<String,Object> innerElements = (Map<String,Object>) elements.get(0);
		distanceDetail = innerElements;
	}
	
	private void setDistanceMap(Map<String,Object> map) {
		distanceMap = map;
	}
	
	private void setDirectionMap(Map<String,Object> map) {
		directionMap = map;
	}
	
	@SuppressWarnings("unchecked")
	private static Map<String,Object> initializeMap(String json) {
		Object jsonObject = reader.read(json);
		Map<String,Object> map = null;
		if(jsonObject instanceof Map) map = (Map<String,Object>) reader.read(json);
		return map;
	}
	
	public String getDistanceStatus() {
		return (String) distanceMap.get("status");
	}

	/**
	 * Get JSON file from google server.
	 * @param url - google api request
	 * @return JSON in String
	 */
	private static String getJSON(String url) {
		InputStream mapJSON = null;
		try {
			mapJSON = new URL(url).openStream();
		} catch (MalformedURLException mf) {
			throw new RuntimeException( mf.getMessage() );
		} catch (IOException io) {
			throw new RuntimeException( io.getMessage() );
		}
		final int bufferSize = 1024;
		final char[] buffer = new char[bufferSize];
		final StringBuilder out = new StringBuilder();
		Reader in = null;
		try {
			in = new InputStreamReader(mapJSON, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException( e.getMessage() );
		}
		for (; ; ) {
		    int rsz = 0;
			try {
				rsz = in.read(buffer, 0, buffer.length);
			} catch (IOException e) {
				throw new RuntimeException( e.getMessage() );
			}
		    if (rsz < 0)
		        break;
		    out.append(buffer, 0, rsz);
		}
		return out.toString();
	}
	
	/**
	 * Return routeMap.
	 * @return Map of route
	 */
	private Map<String,Object> getRouteMap() {
		return routeMap;
	}

	/**
	 * Initialize all detail concern this route to Map.
	 */
	@SuppressWarnings("unchecked")
	private void initializeRouteMap() {
		List<Object> routesList = (List<Object>) directionMap.get("routes");
		Map<String,Object> routesMap = (Map<String,Object>) routesList.get(0);
		List<Object> legsList = (List<Object>) routesMap.get("legs");
		routeMap = (Map<String,Object>) legsList.get(0);
	}
	
	/**
	 * Get distance from JSON.
	 * @return distance in meter
	 */
	public int getDistance() {
		@SuppressWarnings("unchecked")
		Map<String,Object> distanceMap = (Map<String,Object>) getRouteMap().get("distance");
		String distance = String.valueOf( distanceMap.get("value") );
		return Integer.parseInt(distance);
	}
	
	/**
	 * Get duration from JSON.
	 * @return duration in seconds
	 */
	public int getDuration() {
		@SuppressWarnings("unchecked")
		Map<String,Object> durationMap = (Map<String,Object>) getRouteMap().get("duration");
		String duration = String.valueOf( durationMap.get("value") );
		return Integer.parseInt(duration);
	}
	
	/**
	 * Get origin of route.
	 * @return origin of this route
	 */
	public String getOrigin() {
		return (String) getRouteMap().get("start_address");
	}
	
	/**
	 * Get destination of route.
	 * @return destination of this route
	 */
	public String getDestination() {
		return (String) getRouteMap().get("end_address");
	}
	
	/**
	 * Get Route contain every steps in this route.
	 * @return Route that contain every steps in this route
	 */
	public Route getRoute() {
		@SuppressWarnings("unchecked")
		List<Object> stepList = (List<Object>) getRouteMap().get("steps");
		
		return new Route(stepList);
	}
	
	/**
	 * Get status from JSON.
	 * @return status of API request
	 */
	public String getDirectionStatus() {
		return (String) directionMap.get("status");
	}
	
	/**
	 * Get ResourceBundle from config.properties
	 * @return ResourceBundle of config.properties
	 */
	private static ResourceBundle readAPIProperties() {
		FileInputStream file;
		try {
			file = new FileInputStream("src/sensitive_data.properties");
		} catch (FileNotFoundException e) {
			throw new RuntimeException( e.getMessage() );
		}
		ResourceBundle sensitiveData = null;
		try {
			sensitiveData = new PropertyResourceBundle(file);
		} catch (IOException e) {
			throw new RuntimeException( e.getMessage() );
		}
		return sensitiveData;
	}
	
	/**
	 * Read API key from config.properties
	 * @return api key
	 */
	private static String readAPIKey() {
		return readAPIProperties().getString("api");
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
						+ "&language=en&region=th&key=" + readAPIKey();
		if( link.length() > 8192 ) throw new IllegalArgumentException();
		return link;
	}
	
	private static String getDistanceRequest(String origin, String destination) {
		String link = "https://maps.googleapis.com/maps/api/distancematrix/json?origins="
				+ formalize(origin) +"&destinations=" + formalize(destination) + "&language=en&key=" + readAPIKey();
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
