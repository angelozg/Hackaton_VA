
package ch.vaudoise.vaapi.dctm.servlet;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

@Consumes({MediaType.APPLICATION_JSON, "*/json", "*/*+json"})
@Produces({MediaType.APPLICATION_JSON, "*/json", "*/*+json"})
public class VaApiJsonProvider extends JacksonJsonProvider {

}
