package com.zholdak.atmhrp.atm;

public final class Bill {

	private Currency currency;
	private int denomination;
	
	public Bill(Currency currency, int denomination) {
		this.currency = currency;
		this.denomination = denomination;
	}

	public Currency getCurrency() {
		return currency;
	}

	public int getDenomination() {
		return denomination;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((currency == null) ? 0 : currency.hashCode());
		result = prime * result + denomination;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Bill))
			return false;
		Bill other = (Bill) obj;
		if (currency != other.currency)
			return false;
		if (denomination != other.denomination)
			return false;
		return true;
	}
}
