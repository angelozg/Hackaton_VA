
package ch.vaudoise.vaapi.dctm.resource.root;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import ch.vaudoise.vaapi.dctm.model.DeclarationEau;

@Path("declaration")
public class DeclarationResource {


   @PUT
   @Path("eau")
   public Response putToto(DeclarationEau declaration) {

	  System.out.println(declaration.getFirstName());
	  System.out.println(declaration.getName());
	  System.out.println(declaration.getAdress());
	  System.out.println(declaration.getPhoneNumber());
	  System.out.println(declaration.getComment());

	  
	  
      return Response.ok().build();
   }

}
