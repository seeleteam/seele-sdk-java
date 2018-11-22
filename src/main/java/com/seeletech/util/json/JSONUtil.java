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
 * JSON 转换
 *@author
 */
public class JSONUtil {
	public static ObjectMapper objectMapper=null;
	static{
		if(objectMapper==null){
			synchronized(JSONUtil.class) {
				if(objectMapper==null){
					objectMapper=new ObjectMapper();
					//TODO jsonconfig配置
					objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
					objectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
					objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
					objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES,false);
					//将long转String是因为long存在精度丢失的风险
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
			throw new BaseException("JSON转换异常");
		}
	}
	
	public static <T>T toObject(String json,Class<T> c){
		try {
			objectMapper.readValue(json, new TypeReference() {
			});
			return objectMapper.readValue(json, c);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseException("解析JSON异常");
		} 
	}
	
	public static <T>T toObject(String json,TypeReference<T> type){
		try {
			return objectMapper.readValue(json,type);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseException("解析JSON异常");
		} 
	}
	
}
