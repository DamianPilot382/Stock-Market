package com.pilotcraftmc.stockmarket;

import java.math.BigDecimal;
import java.util.ArrayList;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

public class Portfolio {
    
    private BigDecimal balance;
    private ArrayList<PurchasedStock> stocks;
    
    public Portfolio(double balance){
        this.balance = new BigDecimal(balance);
        stocks = new ArrayList<PurchasedStock>();
    }
    
    public void buyStock(Stock s, int amount){
        if(balance.subtract(s.getQuote().getPrice().multiply(new BigDecimal(amount))).doubleValue() >= 0){
        	for(int i = 0; i < stocks.size(); i++){
        		if(stocks.get(i).getSymbol().equals(s.getQuote().getSymbol())){
        			stocks.get(i).addAmount(amount, s.getQuote().getPrice());
                    balance = balance.subtract((s.getQuote().getPrice().multiply(new BigDecimal(amount))));
        			return;
        		}
        	}
            stocks.add(new PurchasedStock(s, amount));
            balance = balance.subtract((s.getQuote().getPrice().multiply(new BigDecimal(amount))));
        }else{
            try {
                throw new NoBalanceException("Not Enough Money, pal.");
            } catch (NoBalanceException e) {
            }
        }
    }
    
    public BigDecimal getBalance(){
        return balance;
    }
    
    public ArrayList<PurchasedStock> getStocks(){
        return stocks;
    }
    
    public BigDecimal sellStock(String symbol, int amount){
        for(int i = 0; i < stocks.size() && stocks.get(i) != null; i++){
            if(stocks.get(i).getSymbol().equals(symbol)){
                if(!stocks.get(i).sell(amount)){
                    try{
                        throw new IllegalArgumentException("Not enough stocks to complete request.");
                    }catch(Exception e){
                    }
                }else{
                	BigDecimal before = new BigDecimal(balance.doubleValue());
                	this.balance = balance.add(YahooFinance.get(stocks.get(i).getSymbol()).getQuote().getPrice().multiply(new BigDecimal(amount)));
                    
                    if(stocks.get(i).getAmount() <= 0){
                    	stocks.remove(i);
                    	i--;
                    }
                	
                	return before.subtract(balance);
                }
            }
            
        }
        
        
        return new BigDecimal(404);
        
    }
    
    public void moveMarket(){
    	for(PurchasedStock ps : stocks){
    		ps.setPriceAverage(ps.getPriceAverage().subtract(new BigDecimal(10)));
    	}
    }
    
}