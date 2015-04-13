package com.cmpe281.team2.miaas.commons.util;

import java.util.HashMap;
import java.util.Map;

public class CacheMapUtil {
	
	private static Map<String, String> cacheMap = null;
	
	public static String get(String key) {
		
		if(cacheMap == null) {
			cacheMap = new HashMap<String, String>();
		}
		
		return cacheMap.get(key);
		
	}
	
	public static void put(String key, String value) {
		if(cacheMap == null) 
			cacheMap = new HashMap<String, String>();
		else
			cacheMap.put(key, value);
	}
	
}
