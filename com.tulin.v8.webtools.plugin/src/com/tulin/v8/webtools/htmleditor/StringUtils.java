package com.tulin.v8.webtools.htmleditor;

/**
 * Provides utility methods for string operation.
 * 
 * @author Naoki Takezoe
 */
public class StringUtils {
	
	public static boolean isEmpty(String value){
		return value == null || value.length() == 0;
	}
	
	public static boolean isNotEmpty(String value){
		return !isEmpty(value);
	}
}
