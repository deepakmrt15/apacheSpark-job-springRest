package com.cit.kyc.cache.utils;

public class ExtractorUtil {
	public static String removeMetaData(String data) {
		if (data==null || data=="") return null;
		if(data.contains("~")) {
			String[] split = data.split("~");
			return split[1];
		} else {
			return data;
		}
	}
}
