package com.zholdak.atmhrp.atm.hrp;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.zholdak.atmhrp.atm.Atm;
import com.zholdak.atmhrp.atm.Bill;
import com.zholdak.atmhrp.atm.Currency;
import com.zholdak.atmhrp.command.Command;
import com.zholdak.atmhrp.command.Execution;
import com.zholdak.atmhrp.command.RegexpCommand;
import com.zholdak.atmhrp.exception.InsufficientFundsException;
import com.zholdak.atmhrp.input.Input;
import com.zholdak.atmhrp.output.Output;

public final class HorseRacePark extends Atm {

	private Map<Integer,Horse> horsesMap = new TreeMap<>();
	private int winnerNum = 1;
	
	/**
	 * 
	 * @param input
	 * @param output
	 */
	public HorseRacePark(Input input, Output output) {
		super(input, output);
		
		registerCommand(new RegexpCommand("^[Ww]\\s+(\\d+)$", setWinnerExecution));
		registerCommand(new RegexpCommand("^(\\d+)\\s+(\\d+(\\.\\d+)?)$", setBetExecution));

		horsesInit();
	}

	/**
	 * 
	 */
	private void horsesInit() throws IllegalArgumentException {
		
		addHorse( new Horse(1, "That Darn Gray Cat", 5), true );
		addHorse( new Horse(2, "Fort Utopia", 10));
		addHorse( new Horse(3, "Count Sheep", 9));
		addHorse( new Horse(4, "Ms Traitour", 4));
		addHorse( new Horse(5, "Real Princess", 3));
		addHorse( new Horse(6, "Pa Kettle", 5));
		addHorse( new Horse(7, "Gin Stinger", 6));
	}

	/**
	 * 
	 * @param horse
	 * @throws IllegalArgumentException
	 */
	private void addHorse(Horse horse) throws IllegalArgumentException {
		addHorse(horse, false);
	}
	
	/**
	 * 
	 * @param horse
	 * @param didWin
	 * @throws IllegalArgumentException
	 */
	private void addHorse(Horse horse, boolean didWin) throws IllegalArgumentException {
		
		if( horsesMap.containsKey( horse.getNum() ) ) {
			throw new IllegalArgumentException( String.format("Horse number '%d' already exists", horse.getNum()) );
		}
		for(Entry<Integer,Horse> entry: horsesMap.entrySet()) {
			if( horse.getName().equals( entry.getValue().getName() ) ) {
				throw new IllegalArgumentException( String.format("Horse named '%s' already exists", horse.getName()) );
			} 
		}
		horsesMap.put( horse.getNum(), horse );
		if( didWin ) {
			winnerNum = horse.getNum();
		}
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	private void horsesOut() throws Exception {
		
		output.out("Horses:");
		for( Entry<Integer,Horse> entry: horsesMap.entrySet() ) {
			Horse horse = entry.getValue();
			output.out( String.format(
					"%d,%s,%d,%s",
					horse.getNum(),
					horse.getName(),
					horse.getOdds(),
					horse.getNum() == winnerNum ? "won" : "lost"
					) );
		}
	}
	
	private Execution setWinnerExecution = new Execution() {
		@Override
		public String execute(Command command, String input) throws Exception {
			
			int num = Integer.parseInt( ((RegexpCommand)command).getArgs(input)[0] );
			if( !horsesMap.containsKey(num) ) {
				return String.format("Invalid Horse Number: %d", num);
			}
			winnerNum = num;
			
			return null;
		}
	};

	private Execution setBetExecution = new Execution() {
		@Override
		public String execute(Command command, String input) throws Exception {
			
			String[] args = ((RegexpCommand)command).getArgs(input);
			
			int num = Integer.parseInt( args[0] );
			if( !horsesMap.containsKey(num) ) {
				return String.format("Invalid Horse Number: %d", num);
			}

			if( args[1].contains(".") ) {
				return String.format("Invalid Bet: %s", args[1]);
			}
			int bet = Integer.parseInt( args[1] );

			Horse horse = horsesMap.get(num);
			
			if( num != winnerNum ) {
				return String.format("No Payout: %s", horse.getName());
			} else {
				int sum = horse.getOdds() * bet;
				try {
					Map<Bill,Integer> map = dispence(sum);
					output.out( String.format("Payout: %s,%d", horse.getName(), sum) );
					outDispensing( map );
				} catch( InsufficientFundsException e ) {
					output.out( String.format("Insufficient Funds: %s", sum) );
				}
			}
			return null;
		}
	};
	
	@Override
	protected void readyOut() throws Exception {
		super.readyOut();
		horsesOut();
	};
	
	@Override
	protected void restockCash() {
		inventory.addCash( new Bill(Currency.USD, 5), 10 );
		inventory.addCash( new Bill(Currency.USD, 100), 10 );
		inventory.addCash( new Bill(Currency.USD, 10), 10 );
		inventory.addCash( new Bill(Currency.USD, 1), 10 );
		inventory.addCash( new Bill(Currency.USD, 20), 10 );
	}
}
