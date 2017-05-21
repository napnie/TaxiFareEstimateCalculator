package com.napnie.tfec;

import java.util.Locale;
import java.util.ResourceBundle;

public class PropertiesUtil {
	
	public static final String SENSITIVE_DATA_BUNDLE = "sensitive_data";
	public static final String CONFIG_BUNDLE = "config";
	private static final Locale locale = new Locale("en");
	
	public static String getProperties(String bundleName, String key) {
		return ResourceBundle.getBundle(bundleName, locale).getString(key);
	}
	
	public static String getAPIKey() {
		return ResourceBundle.getBundle(SENSITIVE_DATA_BUNDLE, locale).getString("api.key");
	}
}
