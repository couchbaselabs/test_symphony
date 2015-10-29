package com.couchbase.test.inf.data.loader;
import org.json.simple.JSONObject;
import java.util.Random;
import java.sql.Timestamp;
import java.util.UUID;
public class RandomDataGenerator {
	 Random randomGenerator = new Random(0);
	 /***
	  * Constructor
	  */
	 public RandomDataGenerator(){
		 randomGenerator = new Random(0);
	 }
	 /***
	  * Constructor
	  * @param seed
	  */
	 public RandomDataGenerator(long seed){
		 randomGenerator = new Random(seed);
	 }
    /***
     * Generate random String using UUID
     * @return
     */
	public  String randomString(){
		return getRandomUUID();
	}
	/****
	 * Initialize random generator
	 * @param seed
	 */
	public  void initializeRandomGenerator(long seed){
		randomGenerator.setSeed(seed);
	}
	/****
	 * Generate random integer based on definition
	 * @param definition
	 * @return
	 */
	public  Integer getRandomInteger(JSONObject definition){
		Integer  start = Integer.MIN_VALUE;
		if(definition.containsKey("start")){
		  start = (Integer) definition.get("start");
		}
		Integer end = Integer.MAX_VALUE;
		if(definition.containsKey("end")){
			  end = (Integer) definition.get("end");
			}
		Integer limit = end - start + 1;
		return start+randomGenerator.nextInt(limit);
	}
	/****
	 * Generate random long based on JSON definition
	 * @param definition
	 * @return
	 */
	public  Long getRandomLong(JSONObject definition){
		Long  start = Long.MIN_VALUE;
		if(definition.containsKey("start")){
		  start = (Long) definition.get("start");
		}
		Long end = Long.MAX_VALUE;
		if(definition.containsKey("end")){
			  end = (Long) definition.get("end");
			}
		Long limit = end - start + 1;
		return start+(randomGenerator.nextLong()*limit);
	}
	/****
	 * Generate random float based on JSON definition
	 * @param definition
	 * @return
	 */
	public  float getRandomFloat(JSONObject definition){
		Float  start = Float.MIN_VALUE;
		if(definition.containsKey("start")){
		  start = (Float) definition.get("start");
		}
		Float end = Float.MAX_VALUE;
		if(definition.containsKey("end")){
			  end = (Float) definition.get("end");
			}
		Float limit = end - start + 1;
		return start+(randomGenerator.nextFloat()*limit);
	}
	/***
	 * Generate random double based on JSON definition
	 * @param definition
	 * @return
	 */
	public  Double getRandomDouble(JSONObject definition){
		Double  start = Double.MIN_VALUE;
		if(definition.containsKey("start")){
		  start = (Double) definition.get("start");
		}
		Double end = Double.MAX_VALUE;
		if(definition.containsKey("end")){
			  end = (Double) definition.get("end");
			}
		Double limit = end - start + 1;
		return start+randomGenerator.nextDouble()*limit;
	}
	/***
	 * Generate random string based on JSON definition
	 * @param definition
	 * @return
	 */
	public String getRandomString(JSONObject definition){
		String  characters = (String) definition.get("characters");
		String  prefix = (String) definition.get("prefix");
		String  suffix = (String) definition.get("suffix");
		Integer  maxStringSize = (Integer) definition.get("maxStringSize");
		StringBuilder string = new StringBuilder();
		if(prefix != null){
			string.append(prefix+"_");
		}
		int length = randomGenerator.nextInt(maxStringSize);
		for(int i=0;i<length;++i){
			int index = randomGenerator.nextInt(length);
			string.append(characters.charAt(index%characters.length()));
		}
		if(suffix != null){
			string.append("_"+suffix);
		}
		
		return string.toString();
	}
	/****
	 * Generate random data time based on definition
	 * @param definition
	 * @return
	 */
	public String getRandomDateTime(JSONObject definition){
		String  startTimeStamp = "2000-01-01 00:00:00.000";
		String  endTimeStamp = "2015-01-01 00:00:00.000";
		if(definition.containsKey("start")){
			startTimeStamp = (String) definition.get("start");
		}
		
		if(definition.containsKey("end")){
			startTimeStamp = (String) definition.get("end");
			}
		long offset = Timestamp.valueOf("2012-01-01 00:00:00").getTime();
		long end = Timestamp.valueOf("2013-01-01 00:00:00").getTime();
		long diff = end - offset + 1;
		Timestamp rand = new Timestamp(offset + (long)(Math.random() * diff));
		return rand.toString();
	}
	/****
	 * Generate random boolean
	 * @return
	 */
	public boolean getRandomBoolean(){
		return randomGenerator.nextBoolean();
	}
	/****
	 * Generate random UUID with '-' removed
	 * @return
	 */
	public String getRandomUUID(){
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replace("-", "");
	}

}
