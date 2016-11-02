
package ch.vaudoise.vaapi.dctm.resource.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import javax.ws.rs.client.Invocation.Builder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import ch.vaudoise.vaapi.dctm.model.Emergency;
import ch.vaudoise.vaapi.dctm.resource.sinistre.EmergencyResource;


@Path("chat")
public class ChatResource {


	@POST
	@Path("")
	public Response postChat(String chatObject) {


		Client client = ClientBuilder.newBuilder().build();
		WebTarget target = client.target("https://api.api.ai/v1/query?v=2015091");

		Builder builder = getBuilder(target);


		Response resp =  builder.post(Entity.entity(chatObject, MediaType.APPLICATION_JSON));

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
		}





		return Response.ok().entity(receiveData).build();
	}

	/**
	 * return builder with correct header
	 *
	 * @param target
	 * @return
	 */
	private Builder getBuilder(WebTarget target) {
		return target.request().header("Authorization", "Bearer 2fc4af45ff184caa8822a303836982f5")
				.accept(MediaType.APPLICATION_JSON);
	}

	// convert InputStream to String
	private static String getStringFromInputStream(InputStream is) {

		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		String line;
		try {

			br = new BufferedReader(new InputStreamReader(is));
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
