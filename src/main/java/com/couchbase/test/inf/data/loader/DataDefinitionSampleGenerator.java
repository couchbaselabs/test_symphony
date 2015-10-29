package com.couchbase.test.inf.data.loader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DataDefinitionSampleGenerator {
	private static String CHARACTERS = "abcdefghijklmoqprstuvwxyz";
	@SuppressWarnings("unchecked")
	public static JSONObject generateBasicDefinition(
			boolean isNameDefinitionPresent, 
			boolean isOptional,
			String type){
		JSONObject definition = new JSONObject();
		if(isNameDefinitionPresent){
			JSONObject nameDefinition = generateNameDefinition();
			definition.put("nameDefinition", nameDefinition);
		}
		if(isOptional){
			definition.put("isOptional", isOptional);
		}
		definition.put("type", type);
		return definition;
	}
	@SuppressWarnings("unchecked")
	public static JSONObject generateNameDefinition(){
		JSONObject nameDefinition = new JSONObject();
		nameDefinition.put("prefix", "nameStartsWith");
		nameDefinition.put("suffix", "nameEndWith");
		nameDefinition.put("maxStringSize", 10);
		nameDefinition.put("characters", CHARACTERS);
		return nameDefinition;
	}
	@SuppressWarnings("unchecked")
	public static JSONObject generateIntegerDefinition(
			boolean isNameDefinitionPresent, 
			boolean isOptional,
			boolean isMinValueGiven,
			boolean isMaxValueGiven){
		JSONObject definition = generateBasicDefinition(isNameDefinitionPresent, isOptional, "integer");
		if(isMinValueGiven){
			definition.put("start", new Integer(0));
		}
		if(isMaxValueGiven){
			definition.put("end",  new Integer(10));
		}
		return definition;
	}
	@SuppressWarnings("unchecked")
	public static JSONObject generateDoubleDefinition(
			boolean isNameDefinitionPresent, 
			boolean isOptional,
			boolean isMinValueGiven,
			boolean isMaxValueGiven){
		JSONObject definition = generateBasicDefinition(isNameDefinitionPresent, isOptional, "double");
		definition.put("type", "double");
		if(isMinValueGiven){
			definition.put("start", new Double(0));
		}
		if(isMaxValueGiven){
			definition.put("end",  new Double(10));
		}
		return definition;
	}
	@SuppressWarnings("unchecked")
	public static JSONObject generateLongDefinition(
			boolean isNameDefinitionPresent, 
			boolean isOptional,
			boolean isMinValueGiven,
			boolean isMaxValueGiven){
		JSONObject definition = generateBasicDefinition(isNameDefinitionPresent, isOptional, "long");
		definition.put("type", "long");
		if(isMinValueGiven){
			definition.put("start", new Long(0));
		}
		if(isMaxValueGiven){
			definition.put("end",  new Long(10));
		}
		return definition;
	}
	@SuppressWarnings("unchecked")
	public static JSONObject generateStringDefinition(
			boolean isNameDefinitionPresent, 
			boolean isOptional,
			String characterString,
			int maxSizeOfString,
			String prefix,
			String suffix){
		JSONObject definition = generateBasicDefinition(isNameDefinitionPresent, isOptional, "string");
		definition.put("type", "string");
		definition.put("prefix", prefix);
		definition.put("suffix", suffix);
		definition.put("maxStringSize", maxSizeOfString);
		definition.put("characters", characterString);
		return definition;
	}
	@SuppressWarnings("unchecked")
	public static JSONObject generateFloatDefinition(
			boolean isNameDefinitionPresent, 
			boolean isOptional,
			boolean isMinValueGiven,
			boolean isMaxValueGiven){
		JSONObject definition = generateBasicDefinition(isNameDefinitionPresent, isOptional, "float");
		definition.put("type", "float");
		if(isMinValueGiven){
			definition.put("start", new Float(0));
		}
		if(isMaxValueGiven){
			definition.put("end",  new Float(10));
		}
		return definition;
	}
	@SuppressWarnings("unchecked")
	public static JSONObject generateDefinition(){
		JSONObject jsonDefinition = new JSONObject();
		JSONObject integerDefintion = generateIntegerDefinition(false, false, true, true);
		JSONObject doubleDefintion = generateDoubleDefinition(false, true, true, true);
		JSONObject stringDefintion = generateStringDefinition(true, true, CHARACTERS, 100, "_prefix_", "_suffix_");
		JSONObject longDefintion = generateLongDefinition(true, false, true, true);
		jsonDefinition.put("integer_name", integerDefintion);
		jsonDefinition.put("double_name", doubleDefintion);
		jsonDefinition.put("string_name", stringDefintion);
		jsonDefinition.put("long_name", longDefintion);
		return jsonDefinition;
	}
	@SuppressWarnings("unchecked")
	public static JSONObject generateJSONObject(Boolean isNameDefinitionPresent, Boolean isOptional){
		JSONObject jsonDefinition = generateBasicDefinition(isNameDefinitionPresent, isOptional, "jsonobject");
		jsonDefinition.put("definitionValue", generateDefinition());
		return jsonDefinition;
	}
	
	@SuppressWarnings("unchecked")
	public static JSONObject generateDefinition(Boolean nesting, Boolean jsonArray){
		JSONObject jsonDefinition = new JSONObject();
		JSONObject integerDefintion = generateIntegerDefinition(false, false, true, true);
		JSONObject doubleDefintion = generateDoubleDefinition(false, true, true, true);
		JSONObject stringDefintion = generateStringDefinition(true, true, CHARACTERS, 100, "_prefix_", "_suffix_");
		JSONObject longDefintion = generateLongDefinition(true, false, true, true);
		jsonDefinition.put("integer_name", integerDefintion);
		jsonDefinition.put("double_name", doubleDefintion);
		jsonDefinition.put("string_name", stringDefintion);
		jsonDefinition.put("long_name", longDefintion);
		if(nesting){
			jsonDefinition.put("jsonobject_name", generateJSONObject(false, true));
		}
		if(jsonArray){
			jsonDefinition.put("jsonobject_name", generateDefinitionWithJSONArray(false, true));
		}
		return jsonDefinition;
	}
	@SuppressWarnings("unchecked")
	public static JSONArray generateDefinitionArray(){
		JSONArray array = new JSONArray();
		JSONObject integerDefintion = generateIntegerDefinition(true, true, true, true);
		JSONObject doubleDefintion = generateDoubleDefinition(true, true, true, true);
		JSONObject stringDefintion = generateStringDefinition(true, true, CHARACTERS, 100, "_prefix_", "_suffix_");
		JSONObject longDefintion = generateLongDefinition(true, true, true, true);
		array.add(integerDefintion);
		array.add(doubleDefintion);
	    array.add(stringDefintion);
	    array.add(longDefintion);
		return array;
	}
	@SuppressWarnings("unchecked")
	public static JSONObject generateDefinitionWithJSONArray(
			Boolean isNameDefinitionPresent, 
			Boolean isOptional){
		JSONObject jsonDefinition = generateBasicDefinition(isNameDefinitionPresent, isOptional, "jsonarray");
		jsonDefinition.put("definitionValue", generateDefinitionArray());
		return jsonDefinition;
	}
	public static void main(String[] args){
		System.out.println(generateFloatDefinition(true, true, true, true).toJSONString());
		System.out.println(generateFloatDefinition(true, false, true, true).toJSONString());
		System.out.println(generateFloatDefinition(false, false, false, false).toJSONString());
		System.out.println(generateIntegerDefinition(true, true, true, true).toJSONString());
		System.out.println(generateIntegerDefinition(true, false, true, true).toJSONString());
		System.out.println(generateIntegerDefinition(false, false, false, false).toJSONString());
		System.out.println(generateDoubleDefinition(true, true, true, true).toJSONString());
		System.out.println(generateDoubleDefinition(true, false, true, true).toJSONString());
		System.out.println(generateDoubleDefinition(false, false, false, false).toJSONString());
		System.out.println(generateDefinition(true, false).toJSONString());
		System.out.println(generateJSONObject(true,true).toJSONString());
		System.out.println(generateJSONObject(false,false).toJSONString());
		System.out.println(generateJSONObject(false,true).toJSONString());
	}
}
