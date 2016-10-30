
package ch.vaudoise.vaapi.dctm.resource.root;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import ch.vaudoise.vaapi.dctm.utils.JsonTransformation;

@Path("")
public class RootResource {

   @GET
   @Path("")
   public Response get() {

      String s = "salut";
      String s2 = "salut2";
      List<String> test = new ArrayList<>();
      test.add(s);
      test.add(s2);

      return Response.ok().entity(JsonTransformation.toJson(test)).build();
   }

   @GET
   @Path("titi/{formId}")
   public Response getTiti(@PathParam("formId") String pFormId) {

      String s = "titi";
      String s2 = "titi2";
      List<String> test = new ArrayList<>();
      test.add(s);
      test.add(s2);
      test.add(pFormId);

      return Response.ok().entity(JsonTransformation.toJson(test)).build();
   }

   @PUT
   @Path("toto")
   public Response putToto(List<String> t) {

      String s = "titi";
      String s2 = "titi2";
      List<String> test = new ArrayList<>();
      test.add(s);
      test.add(s2);
      test.addAll(t);

      return Response.ok().entity(JsonTransformation.toJson(test)).build();
   }

}
