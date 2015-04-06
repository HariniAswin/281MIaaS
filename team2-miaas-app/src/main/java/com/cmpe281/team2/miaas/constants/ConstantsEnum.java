package com.cmpe281.team2.miaas.constants;

import java.util.ArrayList;
import java.util.List;

public class ConstantsEnum {
	
	public enum ResourceType {
		MOBILE_DEVICE ("Mobile Device"), 
		MOBILE_HUB ("Mobile Hub"),
		EMULATOR ("Emulator"),
		SERVER_MACHINE ("Server Machine");
		
		private String name;
		
		private ResourceType(String name) {
			this.name = name;
		}
		
		public String getName() {
			return name;
		}
		
	}
	
	public static List<String> getAllResourceTypes() {
		List<String> resourceTypes = new ArrayList<String>();
		
		ResourceType[] arrayValues = ResourceType.values();
		
		for(ResourceType type : arrayValues) {
			resourceTypes.add(type.getName());
		}
		
		return resourceTypes;
	}
	
}
