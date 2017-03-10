package com.ahang.utils;
import org.springframework.util.StringUtils;
public class DataUtils {

	/**
	 * Object to Long
	 * @param value
	 * @return
	 */
	public static Long toLong(Object value) {
        if (value == null)
            return 0L;
        if (value instanceof Integer) {
            return Long.valueOf(value.toString());
        }
        if (value instanceof Long) {
            return (Long) value;
        }
        if (value instanceof String) {
            if (StringUtils.isEmpty((String) value)){
                return 0L;
            }
            try {
                return Long.valueOf(value.toString());
            } catch (Exception e) {
                return 0L;
            }
        }
        return 0L;
    }

	/**
	 * Object to Integer
	 * @param value
	 * @return
	 */
    public static Integer toInt(Object value) {
        if (value == null) {
            return 0;
        }
        if (value instanceof Integer) {
            return (Integer) value;
        }
        if (value instanceof String) {
            if (StringUtils.isEmpty((String) value)) {
                return 0;
            }
            try {
                return Integer.valueOf(value.toString());
            }catch (Exception e) {
                return 0;
            }
        }
        return 0;
    }
    
    /**
     * Object to Double
     * @param value
     * @return
     */
    public static Double toDouble(Object value){
    	if(value == null){
    		return 0d;
    	}
    	if(value instanceof Double){
    		return (Double) value;
    	}
    	if(value instanceof String ){
    		if (StringUtils.isEmpty((String) value)) {
                return 0d;
            }
    		 try {
                 return Double.valueOf(value.toString());
             }catch (Exception e) {
                 return 0d;
             }
    	}
    	 return 0d;
    }
}
