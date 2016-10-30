
package ch.vaudoise.vaapi.dctm.utils;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class ZonedDateTimeSerializer extends JsonSerializer<ZonedDateTime> {

   @Override
   public void serialize(ZonedDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
      gen.writeString(value.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
   }
}