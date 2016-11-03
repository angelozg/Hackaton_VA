package ch.vaudoise.vaapi.dctm.data;

import java.util.HashMap;
import java.util.Map;

public class Address {

	private static Map<String, String> address = new HashMap<>();
	
	
	static{
		address.put("testuz", "Chemin de Somais 9, 1009 Pully");
		address.put("Angeloz", "route du Merley 16, 1233 Bernex");
	}
	
	public static String getAddress(String name){
		return address.get(name);
	}
}
