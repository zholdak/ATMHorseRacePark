package com.zholdak.atmhrp.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.zholdak.atmhrp.atm.Bill;
import com.zholdak.atmhrp.atm.BillContainer;

public final class Inventory implements Cloneable {

	private List<BillContainer> containersList = new ArrayList<>();
	
	/**
	 * 
	 */
	public Inventory() {
		
	}
	
	/**
	 * 
	 * @param bill
	 * @param amount
	 */
	public void addCash(Bill bill, int amount) {
		for( BillContainer bc: containersList ) {
			if( bill.getDenomination() == bc.getBill().getDenomination() ) {
				bc.setAmount(amount);
				return;
			}
		}
		containersList.add( new BillContainer(bill, amount) );
	}

	/**
	 * 
	 * @return
	 */
	public BillContainer[] getSortedBillContainersArray() {
		return getSortedBillContainersArray(false);
	}

	/**
	 * 
	 * @param reverce
	 * @return
	 */
	public BillContainer[] getSortedBillContainersArray(boolean reverce) {
		BillContainer[] sortedBillContainersArray = containersList.toArray(new BillContainer[containersList.size()]);
		if( reverce ) {
			Arrays.sort(sortedBillContainersArray, Collections.reverseOrder());
		} else {
			Arrays.sort(sortedBillContainersArray);
		}
		return sortedBillContainersArray;
	}
	
	@Override
	public Inventory clone() throws CloneNotSupportedException {
		Inventory clonedInventory = new Inventory(); 
		for( BillContainer bc: containersList ) {
			clonedInventory.containersList.add( bc.clone() );
		}
		return clonedInventory;
	}
}
