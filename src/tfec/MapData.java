package tfec;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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
	static Map<String,Object> directionMap;
	/** All detail concern route. */
	static Map<String,Object> routeMap;
	
	private static final String API_BUNDLE_NAME = "properties.sensitive_data";
	private static final Locale locale = new Locale("en");
	private static final ResourceBundle sd = ResourceBundle.getBundle(API_BUNDLE_NAME, locale);
	
	private MapData(){}
	
	public static Route generateRoute(String origin, String destination) {
		String directionURL = getDirectionRequest(origin, destination);
		
		setDirectionMap( initializeMap( getJSON( directionURL ) ) );
		initializeRouteMap();
		
		return getRoute();
	}
	
	public static int getWaitTime() {
		@SuppressWarnings("unchecked")
		Map<String,Object> durationMap = (Map<String,Object>) getRouteMap().get("duration_in_traffic");
		String duration = String.valueOf( durationMap.get("value") );
		return Integer.parseInt(duration);
	}
	
	private static void setDirectionMap(Map<String,Object> map) {
		directionMap = map;
	}
	
	@SuppressWarnings("unchecked")
	private static Map<String,Object> initializeMap(String json) {
		Object jsonObject = reader.read(json);
		Map<String,Object> map = null;
		if(jsonObject instanceof Map) map = (Map<String,Object>) reader.read(json);
		return map;
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
	private static Map<String,Object> getRouteMap() {
		return routeMap;
	}

	/**
	 * Initialize all detail concern this route to Map.
	 */
	@SuppressWarnings("unchecked")
	private static void initializeRouteMap() {
		List<Object> routesList = (List<Object>) directionMap.get("routes");
		Map<String,Object> routesMap = (Map<String,Object>) routesList.get(0);
		List<Object> legsList = (List<Object>) routesMap.get("legs");
		routeMap = (Map<String,Object>) legsList.get(0);
	}
	
	public static String getPolyline() {
		List<Object> routesList = (List<Object>) directionMap.get("routes");
		Map<String,Object> routesMap = (Map<String,Object>) routesList.get(0);
		Map<String,Object> overview_polyline = (Map<String,Object>) routesMap.get("overview_polyline");
		return String.valueOf( overview_polyline.get("points") );
		
	}
	
	/**
	 * Get distance from JSON.
	 * @return distance in meter
	 */
	private static int getDistance() {
		@SuppressWarnings("unchecked")
		Map<String,Object> distanceMap = (Map<String,Object>) getRouteMap().get("distance");
		String distance = String.valueOf( distanceMap.get("value") );
		return Integer.parseInt(distance);
	}
	
	/**
	 * Get duration from JSON.
	 * @return duration in seconds
	 */
	private static int getDuration() {
		@SuppressWarnings("unchecked")
		Map<String,Object> durationMap = (Map<String,Object>) getRouteMap().get("duration");
		String duration = String.valueOf( durationMap.get("value") );
		return Integer.parseInt(duration);
	}
	
	/**
	 * Get origin of route.
	 * @return origin of this route
	 */
	private static String getOrigin() {
		return (String) getRouteMap().get("start_address");
	}
	
	/**
	 * Get destination of route.
	 * @return destination of this route
	 */
	private static String getDestination() {
		return (String) getRouteMap().get("end_address");
	}
	
	/**
	 * Get Route contain every steps in this route.
	 * @return Route that contain every steps in this route
	 */
	private static Route getRoute() {
		@SuppressWarnings("unchecked")
		List<Object> stepList = (List<Object>) getRouteMap().get("steps");
		Route route = new Route(stepList);
		route.setDestination( getDestination() );
		route.setDistance( getDistance() );
		route.setDuration( getDuration() );
		route.setOrigin( getOrigin() );
		route.setWaitTime( getWaitTime() );;
		return route;
	}
	
	/**
	 * Get status from JSON.
	 * @return status of API request
	 */
	public static String getDirectionStatus() {
		return (String) directionMap.get("status");
	}
	
	/**
	 * Read API key from config.properties
	 * @return api key
	 */
	private static String getAPIKey() {
		String key = "api.key";
		String api = sd.getString(key);
		return api;
	}
	
	/**
	 * Generate Google Maps Directions API request.
	 * @param origin - Origin point
	 * @param destination - Destination point
	 * @return API request to Google Map Direction
	 */
	public static String getDirectionRequest(String origin, String destination) {
		String link = "https://maps.googleapis.com/maps/api/directions/json?origin="
						+ formalize(origin) + "&destination=" + formalize(destination)
						+ "&language=en&region=th&departure_time=now&key=" + getAPIKey();
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
