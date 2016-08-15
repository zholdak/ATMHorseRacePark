package com.zholdak.atmhrp.atm.hrp;

public final class Horse {

	private int num;
	private String name;
	private int odds;
	
	public Horse(int num, String name, int odds) {
		this.num = num;
		this.name = name;
		this.odds = odds;
	}

	public int getNum() {
		return num;
	}

	public String getName() {
		return name;
	}

	public int getOdds() {
		return odds;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + num;
		result = prime * result + odds;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Horse))
			return false;
		Horse other = (Horse) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (num != other.num)
			return false;
		if (odds != other.odds)
			return false;
		return true;
	}
}
