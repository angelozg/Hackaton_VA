package ch.vaudoise.vaapi.dctm.data;

import java.util.HashMap;
import java.util.Map;

public class InsuranceCover {
	
private static Map<String, String> isCover = new HashMap<>();
	
	
	static{
		isCover.put("testuz", "oui");
		isCover.put("angeloz", "non");
		isCover.put("menes", "non");
		isCover.put("deschamps", "oui");
	}
	
	public static String getCover(String name){
		return isCover.get(name);
	}

}

