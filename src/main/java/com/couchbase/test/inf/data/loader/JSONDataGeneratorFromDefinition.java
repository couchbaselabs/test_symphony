package com.couchbase.test.inf.data.loader;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import java.util.HashMap;

public class JSONDataGeneratorFromDefinition {
	public RandomDataGenerator randomDataGenerator;
	public JSONDataGeneratorFromDefinition(long seed){
		randomDataGenerator = new RandomDataGenerator(seed);
	}
    /***
     * Generates Key, Value Pairs based on JSON Object design definition
     * @param seed - seed of random number generator
     * @param number - total number of JSON documents
     * @param keyPrefix - used in key definition
     * @param keySuffix- used in key definition
     * @param dataDefinition - JSON design definition for generating similar JSON objects
     * @return
     */
    public HashMap<String, JSONObject> generateJSONData(int number, String keyPrefix, String keySuffix, JSONObject dataDefinition){
    	HashMap<String, JSONObject> hashMap = new HashMap<String, JSONObject>();
    	for(int i = 0;i<number;++i){
    		String key = keyPrefix+"_"+randomDataGenerator.randomString()+"_"+keySuffix;
    		JSONObject value  = getJSONObject(dataDefinition);
    		hashMap.put(key, value);
    	}
    	return hashMap;
    }
    /***
     * Method to generate JSON data based on JSON definition
     * @param jobject
     * @return
     */
    @SuppressWarnings("unchecked")
	public JSONObject getJSONObject(JSONObject jobject){
    	JSONObject obj = new JSONObject();
    	for(Object k:jobject.keySet()){
    		JSONObject definition = (JSONObject) jobject.get(k);
    		Boolean isOptional = false;
    		if(definition.containsKey("isOptional")){
    			 isOptional = (Boolean )definition.get("isOptional");
    		}
    		String type = (String) definition.get("type");
    		String key = (String)k;
    		if(definition.containsKey("nameDefinition")){
    			JSONObject nameDefinition = (JSONObject) definition.get("nameDefinition");
    			key = randomDataGenerator.getRandomString(nameDefinition);
    		}
    		if(isOptional &&  randomDataGenerator.getRandomBoolean()){
    			isOptional = true;
    		}else{
    			isOptional = false;
    		}
    		if(type == null){
    			obj.put(key,null);
    		}else if(type  == "long"){
    			if(!isOptional){
    				obj.put(key, randomDataGenerator.getRandomLong(definition));
    			}
    		}else if(type  == "integer"){
    			if(!isOptional){
    				obj.put(key, randomDataGenerator.getRandomInteger(definition));
    			}
    		}else if(type  == "double"){
    			if(!isOptional){
    			 obj.put(key, randomDataGenerator.getRandomDouble(definition));
    			}
    		}else if(type  == "boolean"){
    			if(!isOptional){
    			 obj.put(key, randomDataGenerator.getRandomBoolean());
    			}
    		}else if(type  == "datetime"){
    			if(!isOptional){
    				obj.put(key, randomDataGenerator.getRandomDateTime(definition));
    			}
    		}else if(type  == "string"){
    			if(!isOptional){
    				obj.put(key, randomDataGenerator.getRandomString(definition));
    			}
    		}else if(type  == "jsonarray"){
    			if(!isOptional){
    				JSONArray definitionValue = (JSONArray) definition.get("definitionValue");
    				Integer totalElementInArray = (Integer) definition.get("totalElementInArray");
    				obj.put(key, getJSONArray(definitionValue, totalElementInArray));
    			}
    		}else if(type  == "jsonobject"){
    			if(!isOptional){
    				JSONObject definitionValue = (JSONObject) definition.get("definitionValue");
    				obj.put(key, getJSONObject(definitionValue));
    			}
    		}
    	}
    	return obj;
    }
    /****
     * Method to generate JSON Data based on JSON Array
     * This will add JSONArray with n elements
     * @param jarray
     * @param number
     * @return
     */
    @SuppressWarnings({ "unchecked", "unused" })
	public JSONArray getJSONArray(JSONArray jarray, int number){
    	JSONArray array = new JSONArray();
    	int count = 0;
    	while(count < number){
	    	for(Object jobject:jarray){
	    		JSONObject definition = (JSONObject) jobject;
	    		String type = (String) definition.get("type");
	    		if(definition == null){
	    			array.add(null);
	    		}else if(type  == "long"){
	    			array.add(randomDataGenerator.getRandomLong(definition));
	    		}else if(type  == "integer"){
	    			array.add(randomDataGenerator.getRandomInteger(definition));
	    		}else if(type  == "double"){
	    			array.add(randomDataGenerator.getRandomDouble(definition));
	    		}else if(type  == "boolean"){
	    			array.add(randomDataGenerator.getRandomBoolean());
	    		}else if(type  == "datetime"){
	    			array.add(randomDataGenerator.getRandomDateTime(definition));
	    		}else if(type  == "string"){
	    			array.add(randomDataGenerator.getRandomString(definition));
	    		}else if(type  == "jsonarray"){
	    			JSONArray definitionValue = (JSONArray) definition.get("definitionValue");
	    			Integer totalElementInArray = (Integer) definition.get("totalElementInArray");
	    			if(totalElementInArray == null){
	    				totalElementInArray = definitionValue.size();
	    			}
	    			array.add(getJSONArray(definitionValue, totalElementInArray));
	    		}else if(type  == "jsonobject"){
	    			JSONObject definitionValue = (JSONObject) definition.get("definitionValue");
	    			array.add(getJSONObject(definitionValue));
	    		}
	    		++count;
	    	}
    	}
    	return array;
    } 

    @SuppressWarnings("unchecked")
	public static void main(String[] args){
    			// Generate data using a JSON definition - data set generated is based on the protocol 
    			// Change the protocol - change the data generated
    			JSONDataGeneratorFromDefinition jsonDataGeneratorFromDefinition = new JSONDataGeneratorFromDefinition(10);
    			JSONObject jsonDefinition = DataDefinitionSampleGenerator.generateDefinition();
    			JSONObject jsonDefinitionWithJSONArray = new JSONObject();
    			JSONObject jsonDefinition2 = new JSONObject();
    			JSONObject jsonDefinition1 = DataDefinitionSampleGenerator.generateDefinition();
    			jsonDefinition2.put("type", "jsonobject");
    			jsonDefinition2.put("definitionValue", jsonDefinition1);
    			jsonDefinition.put("jsonobject", jsonDefinition2);
    			jsonDefinitionWithJSONArray.put("definitionValue", DataDefinitionSampleGenerator.generateDefinitionArray());
    			jsonDefinitionWithJSONArray.put("type", "jsonarray");
    			jsonDefinitionWithJSONArray.put("totalElementInArray", 10);
    			jsonDefinition.put("jsonarray", jsonDefinitionWithJSONArray);
    			System.out.println(" ---------- DEFINITION ----------");
    			System.out.println(jsonDefinition.toJSONString());
    			System.out.println(" ---------- SAMPLES ----------");
    			HashMap<String, JSONObject>map = jsonDataGeneratorFromDefinition.generateJSONData(10,"prefix", "suffix", jsonDefinition);
    			for(String key:map.keySet()){
    				System.out.println(String.format("%s", map.get(key).toJSONString()));
    			}
    	
    }
   
}
