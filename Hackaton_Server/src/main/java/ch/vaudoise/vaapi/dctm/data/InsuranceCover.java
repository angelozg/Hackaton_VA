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
		isCover.put("hebeisen", "oui");
		isCover.put("morelli", "oui");
		
		
	}
	
	public static String getCover(String name){
		if(isCover.containsKey(name)){
		return isCover.get(name);
		}else{
			return "non";
		}
	}

}

