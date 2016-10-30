
package ch.vaudoise.vaapi.dctm.servlet;

import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import org.glassfish.jersey.server.*;

public class DCTMApplication extends ResourceConfig {

   public DCTMApplication() {
      packages("ch.vaudoise.vaapi.dctm.resource");

      register(VaApiJsonProvider.class, MessageBodyReader.class, MessageBodyWriter.class);

   }
}
