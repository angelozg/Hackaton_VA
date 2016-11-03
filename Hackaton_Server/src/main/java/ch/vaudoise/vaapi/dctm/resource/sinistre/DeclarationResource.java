
package ch.vaudoise.vaapi.dctm.resource.sinistre;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import ch.vaudoise.vaapi.dctm.model.DeclarationEau;
import ch.vaudoise.vaapi.dctm.resource.root.LoadAndSetPDF;
import java.io.IOException;

@Path("declaration")
public class DeclarationResource {


   @PUT
   @Path("eau")
   public Response putToto(DeclarationEau declaration) throws IOException {
       String prenom = declaration.getFirstName();
       String nom = declaration.getName();
       String adresse = declaration.getAdress();
       String telephone = declaration.getPhoneNumber();
       String description = declaration.getDescription();
       String commentaire = declaration.getComment();
       LoadAndSetPDF.setPDF(nom, prenom, adresse, telephone, commentaire);
       return Response.ok().build();
   }

}
