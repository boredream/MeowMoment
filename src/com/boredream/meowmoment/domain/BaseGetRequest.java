package com.boredream.meowmoment.domain;

import java.lang.reflect.Field;

public class BaseGetRequest {
	
	public String toGetString() {
		Class clazz = this.getClass();
		StringBuilder sb = new StringBuilder();
		Field[] fields = clazz.getFields();
		for (int i = 0; i < fields.length; i++) {
			sb.append(i==0?"?":"&");
			sb.append(fields[i].getName());
			sb.append("=");
			try {
				sb.append(fields[i].get(this));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
}
