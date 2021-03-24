package com.glch.base.entity;

public class IntEnum extends BaseEnum {
	private Integer v;

	public int compareTo(BaseEnum e) {
		if (super.compareTo(e) == 1) {
			if (e instanceof IntEnum) {
				if (this == e) {
					return 1;
				} else if (this.compareValue(((IntEnum) e).getValue())) {
					return 1;
				} else {
					return 0;
				}
			} else {
				return 0;
			}
		} else {
			return 0;
		}
	}

	public IntEnum(Integer v, String name) {
		super(v, name);
		if (v != null) {
			this.v = v;
		}else{
			this.v = null;
		}
	}

	public Integer getValue() {
		return v;
	}

	@Override
	public boolean compareValue(Object v) {
		if (this.v == null && v == null) {
			return true;
		} else if (this.v == null || v == null) {
			return false;
		} else if (v instanceof Integer) {
			return this.v == ((Integer) v).intValue();
		} else {
			return false;
		}
	}

}
