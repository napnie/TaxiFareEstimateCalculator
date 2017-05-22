package com.napnie.tfec;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Util class for readind properties.
 * @author Nitith Chayakul
 *
 */
public class PropertiesUtil {
	
	public static final String SENSITIVE_DATA_BUNDLE = "sensitive_data";
	public static final String CONFIG_BUNDLE = "config";
	private static final Locale locale = new Locale("en");
	
	/**
	 * Read properties from select bundle
	 * @param bundleName - name of properties file
	 * @param key - key of property that want to know
	 * @return property of key in properties file
	 */
	public static String getProperties(String bundleName, String key) {
		return ResourceBundle.getBundle(bundleName, locale).getString(key);
	}
	
	/**
	 * Read API key.
	 * @return api key
	 */
	public static String getAPIKey() {
		return ResourceBundle.getBundle(SENSITIVE_DATA_BUNDLE, locale).getString("api.key");
	}
}
