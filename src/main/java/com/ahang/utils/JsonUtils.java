package com.ahang.utils;

import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 	<!-- json -->
		<dependency>
			<groupId>com.google.code.gson</groupId>
			<artifactId>gson</artifactId>
			<version>2.7</version>
		</dependency>
 * @author liuhang
 *
 */
public class JsonUtils {

	private static final Gson gson;

	static {
		gson = new Gson();
	}
	
	/**
	 * json转成对象
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T> T fromJson(String json, Class<T> clazz) {
		if (json == null || json.equals("") || json.equals("null")) {
			return null;
		}
		return JsonUtils.gson.fromJson(json, clazz);
	}

	public static <T> T fromJson(String json, TypeToken<T> typeToken) {
		if (json == null || json.equals("") || json.equals("null")) {
			return null;
		}
		return JsonUtils.gson.fromJson(json, typeToken.getType());
	}

	/**
	 * json转成对象
	 * @param src
	 * @return
	 */
	public static String toJson(Object src) {
		return JsonUtils.gson.toJson(src);
	}

	
	public static String toJsonTree(Object src) {
		return JsonUtils.gson.toJsonTree(src).toString();
	}
	
	
	public static void main(String args[]) throws Exception {
		TestJsonRead test = new TestJsonRead();
		String jsonStr = test.getJsonString();
		System.out.println("jsonStr==" + jsonStr);
		Map map = fromJson(jsonStr,Map.class);
		System.out.println(" map==" + map);
		System.out.println("str==" + toJson(map));
		System.out.println("tree==" + toJsonTree(map));
	}
}
