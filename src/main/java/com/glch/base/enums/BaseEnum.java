package com.glch.base.enums;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;


public abstract class BaseEnum implements Comparable<BaseEnum>,Serializable{
	public static final String LABEL_VALUE = "value";
	public static final String LABEL_NAME = "name";
	private static final long serialVersionUID = -1387978970481121875L;
	protected String name;

	public BaseEnum(Object v,String name){
		this.name = name;
	}
	
	public int compareTo(BaseEnum e) {
		if(e==null){
			return 0;
		}else {
			return 1;
		}
	}
	
	public abstract boolean compareValue(Object v);
	
	public abstract Object getValue();
	
	public static List<? extends BaseEnum> getEnumList(Class<? extends BaseEnum> clazz){
		List<BaseEnum> list = new ArrayList<BaseEnum>();
		Field[] fs = clazz.getDeclaredFields();
		Object item = null;
		int modifier = 0;
		for(int i = 0,len = fs.length;i<len;i++){
			modifier = fs[i].getModifiers();
			if (BaseEnum.class.isAssignableFrom(fs[i].getType())
					&& Modifier.isPublic(modifier)
					&& Modifier.isStatic(modifier)
					&& Modifier.isFinal(modifier)) {
				try {
					item = fs[i].get(null);
				} catch (Exception e) {
					e.printStackTrace();
					item = null;
				}
				if (item != null) {
					if (item instanceof BaseEnum) {
						list.add((BaseEnum) item);
					}
				}
			}
		}
		return list;
	}
	
	public static BaseEnum getEnum(Class<? extends BaseEnum> clazz,Object v){
		BaseEnum e = null;
		if(v!=null){
			List<BaseEnum> list = (List<BaseEnum>) getEnumList(clazz);
			for(int i = 0,size = list.size();i<size;i++){
				e = list.get(i);
				if(e.compareValue(v)) break;
				e = null ;
			}
		}
		return e;
	}
	
	public String getName(){
		return name;
	}
	
	public String toString(){
		if(name == null || name.length()<1){
			return (this.getValue()).toString();
		}else{
			return name;
		}
	}
	
	public Map<String,Object> toMap(){
		Map<String,Object> m = new HashMap<String, Object>(2);
		m.put(LABEL_VALUE, getValue());
		m.put(LABEL_NAME, getName());
		return m;
	}
	
	public JSONObject toJSON(){
		if(getValue()!=null&&getName()!=null){
			JSONObject obj = new JSONObject();
			try {
				obj.put(LABEL_VALUE, getValue());
				obj.put(LABEL_NAME, getName());
			} catch (JSONException e) {}
			return obj;
		}else{
			return null;
		}
	}
}
