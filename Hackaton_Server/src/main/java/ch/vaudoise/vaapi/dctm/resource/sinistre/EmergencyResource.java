
package ch.vaudoise.vaapi.dctm.resource.sinistre;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import ch.vaudoise.vaapi.dctm.model.DeclarationEau;

@Path("emergency")
public class EmergencyResource {


   @PUT
   @Path("eau")
   public Response putToto() {

	  
      return Response.ok().build();
   }

}
