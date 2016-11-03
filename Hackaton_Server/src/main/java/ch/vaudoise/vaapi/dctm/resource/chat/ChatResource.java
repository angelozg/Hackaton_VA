
package ch.vaudoise.vaapi.dctm.resource.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.Normalizer;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import ch.vaudoise.vaapi.dctm.data.Address;
import ch.vaudoise.vaapi.dctm.data.InsuranceCover;
import ch.vaudoise.vaapi.dctm.data.PlumberList;
import ch.vaudoise.vaapi.dctm.model.ChatObject;
import ch.vaudoise.vaapi.dctm.model.Emergency;
import ch.vaudoise.vaapi.dctm.resource.sinistre.EmergencyResource;


@Path("chat")
public class ChatResource {


	@POST
	@Path("")
	public Response postChat(String chatObject) throws JsonParseException, JsonMappingException, IOException {

		String valuePost = new String(chatObject.getBytes("UTF-8"), "UTF-8");
		
		Client client = ClientBuilder.newBuilder().build();
		WebTarget target = client.target("https://api.api.ai/v1/query?v=2015091");

		Builder builder = getBuilder(target);


		Response resp =  builder.post(Entity.entity(valuePost,"application/json; charset=UTF-8"));

		InputStream is = (InputStream) resp.getEntity();
		String receiveData = getStringFromInputStream(is);
		JSONObject root = new JSONObject(receiveData) ;


		switch(root.getJSONObject("result").getString("action")){
		case "emergency_call":
			JSONObject data = root.getJSONObject("result").getJSONArray("contexts").getJSONObject(0).getJSONObject("parameters");
			String type= data.getString("sinister_type");

			Emergency em = new Emergency();
			em.setAdress(data.getString("user_location"));
			em.setFirstName(data.getString("user_firstname"));
			em.setName(data.getString("user_lastname"));


			if(type.equals("dégat d'eau")){
				EmergencyResource.sendMailWater(em);
			}
			break;
		case "confirm.adress":
			JSONObject parameters = root.getJSONObject("result").getJSONArray("contexts").getJSONObject(0).getJSONObject("parameters");
			
			
			parameters.remove("user_location");
			parameters.put("user_location", Address.getAddress(parameters.getString("user_lastname").toLowerCase()));
			
			JSONObject fulfillment = root.getJSONObject("result").getJSONObject("fulfillment");
			String value = fulfillment.getString("speech");
			value =	value.replace("route du Merley 16, 1233 Bernex",Address.getAddress(parameters.getString("user_lastname").toLowerCase()));
			fulfillment.remove("speech");
			fulfillment.put("speech", (String)value);
			root.getJSONObject("result").remove("fulfillment");
			root.getJSONObject("result").put("fulfillment", fulfillment);			
			
			break;
		case "confirm.hasinsurance":
			String userLastname = root.getJSONObject("result").getJSONObject("parameters").getString("user_lastname");	
			userLastname = StringUtils.stripAccents(userLastname);
			String hasCover = InsuranceCover.getCover(userLastname.toLowerCase());
			
			ObjectMapper om = new ObjectMapper();			
			om.configure(org.codehaus.jackson.JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
			ChatObject query = om.readValue(chatObject, ChatObject.class);
			
			query.setQ(hasCover);
			
			//query the APi again
			resp =  builder.post(Entity.entity(query,"application/json; charset=UTF-8"));
			
			is = (InputStream) resp.getEntity();
			receiveData = getStringFromInputStream(is);
			root = new JSONObject(receiveData) ;
			
			// case if there is no cover - we will send an address for some plumber near the locality
			if(hasCover.equals("non")) {
				fulfillment = root.getJSONObject("result").getJSONObject("fulfillment");
				value = fulfillment.getString("speech");
				String location = root.getJSONObject("result").getJSONArray("contexts").getJSONObject(0).getJSONObject("parameters").getString("user_location");
				String[] splitArray = location.split("\\s+");
				String plumberAdr = "";
				for(int i=0; i<splitArray.length; i++) {
					plumberAdr = PlumberList.getPlumber(splitArray[i].toLowerCase());
				}								
				if(StringUtils.isNotEmpty(plumberAdr)) {
					value = value + " " + plumberAdr;
				}				
				fulfillment.remove("speech");
				fulfillment.put("speech", (String)value);
				
				root.getJSONObject("result").remove("fulfillment");
				root.getJSONObject("result").put("fulfillment", fulfillment);
			}
			
			break;
			
		case "ask.emergency":
			fulfillment = root.getJSONObject("result").getJSONObject("fulfillment");
			value = fulfillment.getString("speech");
			
			JSONObject emData = root.getJSONObject("result").getJSONArray("contexts").getJSONObject(0).getJSONObject("parameters");
			String location = emData.getString("user_location");
			String[] splitArray = location.split("\\s+");
			String plumberAdr = "";
			for(int i=0; i<splitArray.length; i++) {
				plumberAdr = PlumberList.getPlumber(splitArray[i].toLowerCase());
			}								
			if(StringUtils.isNotEmpty(plumberAdr)) {
				value = value.replace("XXX", plumberAdr);
				value = value.replace("YYY", location);
			} else {
				value = "Désolé, nous n'avons pas trouvé d'intervenant dans votre secteur ! Souhaitez-vous que je vous aide pour effectuer votre déclaration ?";
			}
			fulfillment.remove("speech");
			fulfillment.put("speech", (String)value);
			
			root.getJSONObject("result").remove("fulfillment");
			root.getJSONObject("result").put("fulfillment", fulfillment);			
		}


		return Response.ok()				
				.entity(root.toString()).build();
	}

	/**
	 * return builder with correct header
	 *
	 * @param target
	 * @return
	 */
	private Builder getBuilder(WebTarget target) {
		return target.request().header("Authorization", "Bearer 2fc4af45ff184caa8822a303836982f5").header("content-type", "application/json;charset=UTF-8")
				.accept(MediaType.APPLICATION_JSON);
	}

	// convert InputStream to String
	private static String getStringFromInputStream(InputStream is) {

		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		String line;
		try {

			br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return sb.toString();

	}


}
