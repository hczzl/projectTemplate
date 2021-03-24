package com.glch.base.enums;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class StringEnum extends BaseEnum {
	private String v;
	
	public StringEnum(String v,String name) {
		super(v,name);
		if(v!=null && v.length()>0){
			this.v = v;
		}else{
			this.v = null;
		}
	}

	public int compareTo(BaseEnum e) {
		if(super.compareTo(e)==1){
			if(e instanceof StringEnum){
				if(this==e){
					return 1;
				}else if(this.compareValue(((StringEnum)e).getValue())){
					return 1;
				}else{
					return 0;
				}
			}else{
				return 0;
			}
		}else{
			return 0;
		}
	}
	
	@Override
	public String getValue(){
		return v;
	}

	@Override
	public boolean compareValue(Object v) {
		if (this.v == null && v == null) {
			return true;
		} else if (this.v == null || v == null) {
			return false;
		} if(v instanceof String){
			return this.v.equals((String)v);
		}else{
			return false;
		}
	}
	
	public static List getEnumListWithLimit(Class clazz, Set<String> valueLimitSet){
		List<BaseEnum> allList = (List<BaseEnum>) getEnumList(clazz);
		if(valueLimitSet == null){
			return allList;
		}
		List resultList = new ArrayList();
		for(BaseEnum e : allList){
			if(valueLimitSet.contains(e.getValue())){
				resultList.add(e);
			}
		}
		return resultList;
	}
}
