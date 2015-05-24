package com.pilotcraftmc.stockmarket;

@SuppressWarnings("serial")
public class NoBalanceException extends Exception {
	
	public NoBalanceException(){
		
	}
	
	public NoBalanceException(String message){
		super(message);
	}

}
