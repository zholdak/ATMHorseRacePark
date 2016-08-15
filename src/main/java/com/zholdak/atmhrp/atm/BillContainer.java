package com.zholdak.atmhrp.atm;

import com.zholdak.atmhrp.exception.BillContainerEmptyException;

public class BillContainer implements Comparable<BillContainer>, Cloneable {

	private Bill bill;
	private int amount;
	
	public BillContainer(Bill bill, int amount) {
		this.bill = bill;
		this.amount = amount;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public void withdrawOne() {
		if( this.amount == 0 ) {
			throw new BillContainerEmptyException(String.format("No more %s%d bills", bill.getCurrency(), bill.getDenomination()));
		}
		this.amount -= 1;
	}
	
	public Bill getBill() {
		return bill;
	}

	@Override
	public int compareTo(BillContainer bc) {
		return getBill().getDenomination() - bc.getBill().getDenomination();
	}
	
	@Override
	public BillContainer clone() throws CloneNotSupportedException {
		return new BillContainer(
				new Bill(bill.getCurrency(), bill.getDenomination()),
				amount);
	}
}
