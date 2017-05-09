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
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * Send request to Google Maps Directions API.
 * @author Nitith Chayakul
 *
 */
public class MapData {
	
	/**
	 * Get JSON file from google server.
	 * @param url - google api request
	 * @return JSON file
	 */
	public InputStream getJSON(String url) {
		InputStream mapJSON = null;
		try {
			mapJSON = new URL(url).openStream();
		} catch (MalformedURLException mf) {
			throw new RuntimeException( mf.getMessage() );
		} catch (IOException io) {
			throw new RuntimeException( io.getMessage() );
		}
		return mapJSON;
	}
	
	/**
	 * Get JSON in String from InputStream.
	 * @param json - InputStream of JSON
	 * @return JSON in String
	 */
	public String getJSONInString(InputStream json) {
		final int bufferSize = 1024;
		final char[] buffer = new char[bufferSize];
		final StringBuilder out = new StringBuilder();
		Reader in = null;
		try {
			in = new InputStreamReader(json, "UTF-8");
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
	 * Get ResourceBundle from config.properties
	 * @return ResourceBundle of config.properties
	 */
	private ResourceBundle readAPIProperties() {
		FileInputStream file;
		try {
			file = new FileInputStream("src/sensitive_data.properties");
		} catch (FileNotFoundException e) {
			throw new RuntimeException( e.getMessage() );
		}
		ResourceBundle config = null;
		try {
			config = new PropertyResourceBundle(file);
		} catch (IOException e) {
			throw new RuntimeException( e.getMessage() );
		}
		return config;
	}
	
	/**
	 * Read API key from config.properties
	 * @return api key
	 */
	private String readAPIKey() {
		return readAPIProperties().getString("api");
	}
	
	/**
	 * Generate Google Maps Directions API request.
	 * @param origin - Origin point
	 * @param destination - Destination point
	 * @return API request to Google Map Direction
	 */
	public String getDirectionRequest(String origin, String destination) {
		String link = "https://maps.googleapis.com/maps/api/directions/json?origin="
						+ formalize(origin) + "&destination=" + formalize(destination)
						+ "&language=en&region=th&key=" + readAPIKey();
		if( link.length() > 8192 ) link = null;
		return link;
	}
	
	/**
	 * Formalize string so that it fit API request requirement.
	 * @param string - String that want to formalize
	 * @return formalized String
	 */
	private String formalize(String string) {
		return formalize(string, 0);
	}

	/**
	 * Helper method for formalize String.
	 * @param string
	 * @param index
	 * @return
	 */
	private String formalize(String string, int index) {
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
