
package ch.vaudoise.vaapi.dctm.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class DCTMDateSerializer extends JsonSerializer<Date> {

   public static final DCTMDateSerializer INSTANCE         = new DCTMDateSerializer();

   //format yyyy-MM-dd
   private final SimpleDateFormat         simpleDateFormat = new SimpleDateFormat("dd/mm/yyyy");

   @Override
   public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
      gen.writeString(simpleDateFormat.format(value));
   }
}