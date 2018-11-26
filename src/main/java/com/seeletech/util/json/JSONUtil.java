package com.seeletech.util.json;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.seeletech.util.exception.BaseException;
import java.util.TimeZone;

/**
 *
 *@author
 */
public class JSONUtil {

	public static ObjectMapper objectMapper=null;
	static{
		if(objectMapper==null){
			synchronized(JSONUtil.class) {
				if(objectMapper==null){
					objectMapper=new ObjectMapper();
					objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
					objectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
					objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
					objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES,false);

					SimpleModule module = new SimpleModule();  
					module.addSerializer(Long.class, ToStringSerializer.instance);
					objectMapper.registerModule(module); 
					objectMapper.setSerializationInclusion(Include.NON_NULL);
					objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
				}
			}
		}
	}
	
	public static String toJSON(Object object){
		try {
			return objectMapper.writeValueAsString(object);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseException("JSON conversion exception");
		}
	}
	
	public static <T>T toObject(String json,Class<T> c){
		try {
			objectMapper.readValue(json, new TypeReference() {
			});
			return objectMapper.readValue(json, c);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseException("analysis JSON exception");
		} 
	}
	
	public static <T>T toObject(String json,TypeReference<T> type){
		try {
			return objectMapper.readValue(json,type);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseException("analysis JSON exception");
		} 
	}
}
