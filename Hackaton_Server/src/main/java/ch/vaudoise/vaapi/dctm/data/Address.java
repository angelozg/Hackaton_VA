package ch.vaudoise.vaapi.dctm.data;

import java.util.HashMap;
import java.util.Map;

public class Address {

	private static Map<String, String> address = new HashMap<>();


	static{
		address.put("testuz", "Chemin de Somais 9, 1009 Pully");
		address.put("angeloz", "route du Merley 16, 1233 Bernex");
		address.put("cherpillod", "route du Sanetsch 99, 1950 Sion");
		address.put("cornaz", "route de Villars 37, 1700 Fribourg");
		address.put("hebeisen", "Weissensteinstrasse 61, 3007 Bern");
		address.put("morelli", "chemin des Chapons-des-Prés 7, 2022 Bevaix ");
		address.put("dufresne", "chemin des Bosquets, 1400 Yverdon-les-Bains");
		address.put("moosman", "rue du Lac 75, 1815 Clarens");
		address.put("perinetti", "chemin du Château-Sec 38, 1510 Moudon");
		address.put("longchamp", "avenue de la Gare 3, 2000 Neuchâtel");
		address.put("simonin", "rue de la Confédération 8, 1204 Genève");
		address.put("menes", "avenue du Cardinal-Mermillod 36, 1227 Carouge");
	}

	public static String getAddress(String name){
		if(address.containsKey(name)){
			return address.get(name);
		}else{
			return "route du Merley 16, 1233 Bernex";
		}
	}
}
