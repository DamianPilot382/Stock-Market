package com.pilotcraftmc.stockmarket;

import java.math.BigDecimal;

import yahoofinance.Stock;

public class PurchasedStock {	

	private String symbol;
	private BigDecimal priceAverage;
	private int shareAmount;

	public PurchasedStock(Stock stock, int amount){
		this.shareAmount = amount;
		symbol = stock.getSymbol();
		priceAverage = stock.getQuote().getPrice();
	}
	
	public String getSymbol(){
		return symbol;
	}
	
	public BigDecimal getPriceAverage(){
		return priceAverage;
	}
	
	public int getAmount(){
		return shareAmount;
	}
	
	public boolean sell(int amount){
		if(this.shareAmount - amount >= 0){
			this.shareAmount -= amount;
			return true;
		}else{
			return false;
		}
	}
	
	public void setAmount(int amount){
		this.shareAmount = amount;
	}
	
	public void addAmount(int amount, BigDecimal price){
		BigDecimal lastAverage = new BigDecimal(priceAverage.doubleValue()).multiply(new BigDecimal(this.shareAmount));
		BigDecimal addAverage = new BigDecimal(price.multiply(new BigDecimal(amount)).doubleValue());
		BigDecimal thirdStep = lastAverage.add(addAverage);
		
		priceAverage = thirdStep.divide(new BigDecimal(amount + shareAmount));
		this.shareAmount += amount;
		
	}
	
	public void setPriceAverage(BigDecimal a){
		this.priceAverage = a;
	}
	
}
