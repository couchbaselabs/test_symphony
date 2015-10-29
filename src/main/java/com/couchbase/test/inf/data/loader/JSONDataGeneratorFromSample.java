package com.couchbase.test.inf.data.loader;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import java.util.HashMap;
public class JSONDataGeneratorFromSample  {
	public RandomDataGenerator randomDataGenerator;
	public JSONDataGeneratorFromSample(long seed){
		randomDataGenerator = new RandomDataGenerator(seed);
	}
    /***
     * Generates Key, Value Pairs based on JSON Object
     * @param number - total number of JSON documents
     * @param keyPrefix - used in key definition
     * @param keySuffix- used in key definition
     * @param dataDefinition - JSON documents for generating similar JSON objects
     * @return
     */
    public  HashMap<String, JSONObject> generateJSONData(
    		int number, 
    		String keyPrefix, 
    		String keySuffix, 
    		JSONObject dataDefinition){
    	HashMap<String, JSONObject> hashMap = new HashMap<String, JSONObject>();
    	for(int i = 0;i<number;++i){
    		String key = keyPrefix+"_"+randomDataGenerator.randomString()+"_"+keySuffix;
    		JSONObject value  = getJSONObject(dataDefinition);
    		hashMap.put(key, value);
    	}
    	return hashMap;
    }
    /***
     * Method used to generate JSON data from a JSON Object
     * @param jobject
     * @return
     */
    @SuppressWarnings("unchecked")
	public  JSONObject getJSONObject(JSONObject jobject){
    	JSONObject obj = new JSONObject();
    	for(Object k:jobject.keySet()){
    		Object definition = jobject.get(k);
    		if(definition == null){
    			obj.put(k,null);
    		}else if(definition.getClass() == Integer.class){
    			obj.put(k, randomDataGenerator.randomGenerator.nextInt());
    		}else if(definition.getClass() == Double.class){
    			obj.put(k, randomDataGenerator.randomGenerator.nextDouble());
    		}else if(definition.getClass() == Float.class){
    			obj.put(k,randomDataGenerator.randomGenerator.nextFloat());
    		}else if(definition.getClass() == Long.class){
    			obj.put(k, randomDataGenerator.randomGenerator.nextLong());
    		}else if(definition.getClass() == Boolean.class){
    			obj.put(k, randomDataGenerator.randomGenerator.nextBoolean());
    		}else if(definition.getClass() == String.class){
    			obj.put(k, randomDataGenerator.randomString());
    		}else if(definition.getClass() == JSONObject.class){
    			obj.put(k, getJSONObject((JSONObject) definition));
    		}else if(definition.getClass() == JSONArray.class){
    			obj.put(k, getJSONArray((JSONArray) definition));
    		}
    	}
    	return obj;
    }
    /****
     * Method to used to generate data from JSON Object - targeted towards JSONArray
     * @param jsonArray
     * @return
     */
    @SuppressWarnings("unchecked")
	public  JSONArray getJSONArray(JSONArray jsonArray){
    	JSONArray definedJSONArray = new JSONArray();
    	for(Object definition:jsonArray){
    		if(definition == null){
    			definedJSONArray.add(null);
    		}else if(definition.getClass() == Integer.class){
    			definedJSONArray.add(randomDataGenerator.randomGenerator.nextInt());
    		}else if(definition.getClass() == Double.class){
    			definedJSONArray.add(randomDataGenerator.randomGenerator.nextDouble());
    		}else if(definition.getClass() == Float.class){
    			definedJSONArray.add(randomDataGenerator.randomGenerator.nextFloat());
    		}else if(definition.getClass() == Boolean.class){
    			definedJSONArray.add(randomDataGenerator.randomGenerator.nextBoolean());
    		}else if(definition.getClass() == String.class){
    			definedJSONArray.add(randomDataGenerator.randomString());
    		}else if(definition.getClass() == JSONObject.class){
    			definedJSONArray.add(getJSONObject((JSONObject)definition));
    		}else if(definition.getClass() == JSONArray.class){
    			definedJSONArray.add(getJSONArray((JSONArray)definition));
    		}
    	}
    	return definedJSONArray;
    }
    
	@SuppressWarnings("unchecked")
	public static void main(String[] args){
	
		// Generate Data using JSON as definition - similar looking data i.e. the basic structure is preserved
		JSONObject obj = new JSONObject();
		JSONObject obj1 = new JSONObject();
		JSONArray array = new JSONArray();
		obj1.put("string", new String("ddd"));
		obj1.put("integer", new Integer(33));
		obj1.put("long", new Long(33));
		obj1.put("double", new Double(33));
		obj.put("string", new String("ddd"));
		obj.put("integer", new Integer(33));
		obj.put("long", new Long(33));
		obj.put("null", null);
		obj.put("double", new Double(33));
		obj.put("jsonObject", obj1);
		array.add("string example");
		obj.put("jsonarray", array);
		JSONDataGeneratorFromSample jsonDataGeneratorFromSample = new JSONDataGeneratorFromSample(0);
		System.out.println(" ---------- DEFINITION ----------");
		System.out.println(obj.toJSONString());
		HashMap<String, JSONObject> map = jsonDataGeneratorFromSample.generateJSONData(10,"prefix", "suffix", obj);
		System.out.println(" ---------- SAMPLES ----------");
		for(String key:map.keySet()){
			System.out.println(String.format("%s",map.get(key).toJSONString()));
		}
		
		
	}
}
