package com.zholdak.atmhrp.atm;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.zholdak.atmhrp.command.Command;
import com.zholdak.atmhrp.command.Execution;
import com.zholdak.atmhrp.command.SimpleCommand;
import com.zholdak.atmhrp.exception.CommandAlreadyRegisteredException;
import com.zholdak.atmhrp.exception.InsufficientFundsException;
import com.zholdak.atmhrp.exception.Inventory;
import com.zholdak.atmhrp.input.Input;
import com.zholdak.atmhrp.output.Output;

public abstract class Atm {

	protected Input input;
	protected Output output;
	protected Map<String, Command> commands = new HashMap<>();
	protected Inventory inventory = new Inventory();
	
	private Execution restockExecution = new Execution() {
		@Override
		public String execute(Command command, String input) throws Exception {
			restockCash();
			return null;
		}
	};

	private Command restockCmd = new SimpleCommand("r", restockExecution);
	private Command quitCmd = new SimpleCommand("q");
	
	public Atm(Input input, Output output) {
		
		this.input = input;
		this.output = output;
		
		registerCommand(quitCmd);
		registerCommand(restockCmd);

		restockCash();
	}

	/**
	 * 
	 * @param command
	 */
	protected void registerCommand(Command command) {
		
		if( commands.containsKey(command.getCommand()) ) {
			throw new CommandAlreadyRegisteredException(command.getCommand());
		}
		
		commands.put(command.getCommand(), command);
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	public void start() throws Exception {

		input.open();
		output.open();
		
		try {
		
			while( true ) {
				
				readyOut();
				
				String inp = input.get();

				boolean ok = false;
				
				for( Entry<String, Command> entry: commands.entrySet() ) {
					
					Command cmd = entry.getValue();
					if( cmd.matches(inp) ) {
						if( cmd.equals(quitCmd) ) {
							return;
						}
						output.out( cmd.execute(inp) );
						ok = true;
					}
				}
				
				if( !ok ) {
					output.out( String.format("Invalid Command: %s", inp) );
				}
			}
			
		} finally {
			
			output.close();
			input.close();
		}
	}

	protected synchronized Map<Bill,Integer> dispence(int sum) throws CloneNotSupportedException {
		
		Inventory modifiedInventory = inventory.clone();
		
		int rest = sum;
		
		Map<Bill,Integer> map = new HashMap<>();
		
		for( BillContainer bc: modifiedInventory.getSortedBillContainersArray(true) ) {
			while( bc.getAmount() > 0 ) {
				if( rest < bc.getBill().getDenomination() ) {
					break;
				} else {
					if( map.containsKey(bc.getBill()) ) {
						map.put(bc.getBill(), map.get(bc.getBill()) + 1);
					} else {
						map.put(bc.getBill(), 1);
					}
					rest -= bc.getBill().getDenomination();
					bc.withdrawOne();
				}
			}
		}
		
		if( rest > 0 ) {
			throw new InsufficientFundsException( String.format("%d", sum) );
		}
		
		inventory = modifiedInventory;
		return map;
	}

	/**
	 * 
	 */
	protected void outInventory() throws Exception {
		
		output.out( "Inventory:" );
		for( BillContainer bc: inventory.getSortedBillContainersArray() ) {
			output.out( String.format("%s%d,%d", bc.getBill().getCurrency().toString(), bc.getBill().getDenomination(), bc.getAmount()) );
		}
	}

	/**
	 * 
	 */
	protected void outDispensing(Map<Bill,Integer> map) throws Exception {

		output.out("Dispensing:");
		for( BillContainer bc: inventory.getSortedBillContainersArray() ) {
			Integer count = map.get(bc.getBill());
			output.out(String.format("%s%d: %d", bc.getBill().getCurrency(), bc.getBill().getDenomination(), count == null ? 0 : count));
		}
	}	
	
	/**
	 * @throws Exception 
	 * 
	 */
	protected void readyOut() throws Exception {
		outInventory();
	}
	
	/**
	 * 
	 */
	protected abstract void restockCash();
}
