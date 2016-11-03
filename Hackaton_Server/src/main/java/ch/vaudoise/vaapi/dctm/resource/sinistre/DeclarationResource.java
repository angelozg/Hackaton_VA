
package ch.vaudoise.vaapi.dctm.resource.sinistre;

import javax.ws.rs.core.Response;

import ch.vaudoise.vaapi.dctm.model.DeclarationEau;
import ch.vaudoise.vaapi.dctm.resource.root.LoadAndSetPDF;
import java.io.IOException;


public class DeclarationResource {


   public static Response createDeclaration(DeclarationEau declaration) throws IOException {
       String prenom = declaration.getFirstName();
       String nom = declaration.getName();
       String adresse = declaration.getAdress();
       String telephone = declaration.getPhoneNumber();
       String commentaire = declaration.getComment();
       String description = declaration.getDescription();
       LoadAndSetPDF.setPDF(nom, prenom, adresse, telephone, commentaire,description);
       return Response.ok().build();
   }

}
