package com.glch.base.enums;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

public class BaseEnumTypeHandler<E extends BaseEnum> extends BaseTypeHandler<E> {
	private Class<E> enumType;
	private List<E> enumList = new ArrayList<E>(); 
	
	public BaseEnumTypeHandler(Class<E> type){
		if(type ==null){
			throw new IllegalArgumentException("Type argument cannot be null");
		}
		this.enumType = type;
		E[] enums = enumType.getEnumConstants();
		if(enums == null){
			throw new IllegalArgumentException(type.getSimpleName()+" does not represent an enum type.");
		}
		for(E e : enums){
			enumList.add(e);
		}
		
	}

	@Override
	public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
		// TODO Auto-generated method stub
		return getEnum(rs.getObject(columnName));
	}

	@Override
	public E getNullableResult(ResultSet rs, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		return getEnum(rs.getObject(arg1));
	}

	@Override
	public E getNullableResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		// TODO Auto-generated method stub
		return getEnum(cs.getObject(columnIndex));
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, E parameter,
			JdbcType jdbcType) throws SQLException {
		// TODO Auto-generated method stub
		ps.setObject(i, parameter.getValue(), jdbcType.TYPE_CODE);
	}
	

	public E getEnum(Object v){
		E e = null;
		if(v!=null){
			for(int i = 0,size = enumList.size();i<size;i++){
				e = enumList.get(i);
				if(e.compareValue(v)) break;
				e = null ;
			}
		}
		return e;
	}
}
