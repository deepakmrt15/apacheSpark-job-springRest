package com.ds.spring.spark.example.utils;

import java.text.SimpleDateFormat;

public class CommonUtils {

	public static String removeMetaData(String data) {
		if (data==null || data=="") return null;
		if(data.contains("~")) {
			String[] split = data.split("~");
			return split[1];
		} else {
			return data;
		}
	}

	public static Object dateFormatter(Object data) {
		SimpleDateFormat td = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");

		if(data!=null) {
			return td.format(data);
		} else {
			return data;
		}

	}
}
