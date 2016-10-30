
package ch.vaudoise.vaapi.dctm.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonTransformation {

   public static String toJson(Object ob) {
      ObjectMapper mapper = new ObjectMapper();

      String result = null;
      try {
         result = mapper.writeValueAsString(ob);
      }
      catch (JsonProcessingException e) {
         e.printStackTrace();
      }

      return result;
   }

}
