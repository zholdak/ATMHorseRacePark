package com.zholdak.atmhrp.atm;

public enum Currency {

	USD("$");
	
	private final String sign;
	
	Currency(String sign) {
		this.sign = sign;
	}
	
	@Override
	public String toString() {
		return sign;
	}
}
