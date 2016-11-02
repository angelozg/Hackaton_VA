
package ch.vaudoise.vaapi.dctm.resource.sinistre;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import ch.vaudoise.vaapi.dctm.model.DeclarationEau;

@Path("declaration")
public class DeclarationResource {


   @PUT
   @Path("eau")
   public Response putToto(DeclarationEau declaration) {


	  
	  
      return Response.ok().build();
   }

}
