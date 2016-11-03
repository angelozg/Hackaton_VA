package ch.vaudoise.vaapi.dctm.data;

import java.util.HashMap;
import java.util.Map;

public class PlumberList {
	
private static Map<String, String> plumberList = new HashMap<>();
	
	
	static{
		plumberList.put("lausanne", "Aqua Tech 3000 S�rl 0213493344");
		plumberList.put("gen�ve", "Souza S�rl 0223994848");
		plumberList.put("montreux", "Hydro Pro SA 0343991212");
		plumberList.put("neuch�tel", "Simon Cuany 0784486767");
	}
	
	public static String getPlumber(String name){		
		return plumberList.get(name);
	}

}

