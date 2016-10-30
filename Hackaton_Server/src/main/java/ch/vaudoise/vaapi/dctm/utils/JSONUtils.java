
package ch.vaudoise.vaapi.dctm.utils;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Class for JSON utilities.
 *
 * @author
 */
public final class JSONUtils {

   /**
    * @param jsonData
    * @param jsonKey
    * @return
    * @throws Exception
    */
   @Deprecated
   public static String extractJSONValue(String jsonData, String jsonKey, String regexCharactersExcluded) throws IllegalArgumentException {
      // Json data is not empty
      if (jsonData != null && jsonData.trim().length() > 0) {
         // Split the json data on the given key
         String[] split = jsonData.toLowerCase().split(jsonKey.toLowerCase());
         // The resulting array of splitted elements has more than 1 element
         if (split != null && split.length > 1) {
            // Split again on the , and take the left part, then remove all excluded characters if any
            String secondSplit = split[1].split(",")[0].replaceAll("[\":}{]", "");
            return regexCharactersExcluded != null && regexCharactersExcluded.trim().length() > 0 ? secondSplit.replaceAll(regexCharactersExcluded, "") : secondSplit;
         }

         throw new IllegalArgumentException("The given json data does not contain the key [" + jsonKey + "] !");
      }

      throw new IllegalArgumentException("The given json data is empty!");
   }

   /**
    * Extract parameters from a json structure
    * Ex of an array structure : {"filters":[{"field":"c_cat_2","operator":"Eq","value":"08042308800aef90"},{"field":"contractId","operator":"Eq","value":""}],"sorts":[{"field":"signatureDate","direction":"Desc"}]}
    * The output of this method for "filters" will be : [{"field":"c_cat_2","operator":"Eq","value":"08042308800aef90"},{"field":"contractId","operator":"Eq","value":""}]
    * Example of a non array JSON : {"id":"1","name":"name = 'vva_gestionnaire_courrier_05'"}
    *
    * @param :
    *           JSON strucutre
    * @param pParamName
    * @param indicates
    *           if the param to be extracted is an Array
    * @return
    */
   public static String extractParameters(String pData, String pParamName, boolean isArray) {
      JSONObject root = new JSONObject(pData);

      if (isArray) {
         JSONArray paramArray = root.getJSONArray(pParamName);
         return paramArray.toString();
      } else {
         // Handling special case where paramName does not exist : example : {"id":"1","name":null}. When it is set, it is like : {"id":"1","name":"name = 'vva_gestionnaire_courrier_05'"}
         return root.get(pParamName).toString().equals("null") ? "" : root.getString(pParamName);
      }

   }

}
