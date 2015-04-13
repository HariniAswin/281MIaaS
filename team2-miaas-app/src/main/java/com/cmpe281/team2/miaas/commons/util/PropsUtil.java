package com.cmpe281.team2.miaas.commons.util;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;


public class PropsUtil {
	private static Configuration config;
	
	private static Configuration getConfig() {
		if (config == null) {
			try {
				config = new PropertiesConfiguration("application.properties");
			} catch (ConfigurationException e) {
				e.printStackTrace();
			}
		}
		return config;
	}
	
	public static String get(String key) {
		return getConfig().getString(key);
	}
}