package com.pilotcraftmc.stockmarket;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;
import java.util.logging.LogManager;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

public class StockMarket {
		
	public static void main(String[] args) {
		userInterface();
	}
	
	public static void userInterface(){
		LogManager.getLogManager().reset();
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		System.out.print("Initial Balance: $");
		Portfolio p = new Portfolio(in.nextDouble());
		while(true){
			System.out.println("1.- Buy Stocks");
			System.out.println("2.- Sell Stocks");
			System.out.println("3.- View Current Balance");
			System.out.println("4.- View Current Stocks");
			System.out.println("5.- View a Stock");
			System.out.print("Enter a command: ");
			int command = in.nextInt();
			switch(command){
				case 1:
					System.out.print("Enter a Stock Symbol: ");
					String symbolBuy = in.next().toUpperCase();
					System.out.print("Enter an amount: ");
					int amountBuy = in.nextInt();
					try{
						p.buyStock(stock(symbolBuy), amountBuy);
					}catch(Exception e){
						System.out.println("Not enough money for the purchase.");
					}
					break;
				case 2:
					System.out.print("Enter a Stock Symbol: ");
					String symbolSell = in.next().toUpperCase();
					System.out.print("Enter an amount: ");
					int amountSell = in.nextInt();
					p.sellStock(symbolSell, amountSell);
					break;
				case 3:
					System.out.printf("Current Balance: $%.2f", p.getBalance());
					break;
				case 4:
					System.out.println("Current Stocks: ");
					for(PurchasedStock s : p.getStocks()){
						
						Stock temp = YahooFinance.get(s.getSymbol());
						
						System.out.println("===============================");
						System.out.println("Symbol: \t" + temp.getSymbol());
						System.out.println("Name: \t\t" + temp.getName());
						System.out.println(temp.getQuote());
						System.out.println();
						System.out.println("Amount in portfolio: \t" + s.getAmount());
						System.out.printf("Dollar Cost Average: \t%.2f\n", s.getPriceAverage());
						System.out.println("Current market price: \t" + temp.getQuote().getPrice());
						if(s.getPriceAverage().compareTo(temp.getQuote().getPrice()) == 0){
							System.out.println("Change: $0.0");
							System.out.println("Percent Change: 0.0%");
						}else if(s.getPriceAverage().compareTo(temp.getQuote().getPrice()) <= 0){
							System.out.printf("Change: $%.2f\n", (s.getPriceAverage().subtract(temp.getQuote().getPrice())));
							System.out.printf("Percent Change: %.2f%%\n", percent(s.getPriceAverage(), temp.getQuote().getPrice()));
						}else{
							System.out.printf("Change: +%.2f\n", (temp.getQuote().getPrice().subtract(s.getPriceAverage())));
							System.out.printf("Percent Change: +%.2f%%\n", percent(temp.getQuote().getPrice(), s.getPriceAverage()));
						}
						System.out.println("===============================");
						
					}
					break;
				case 5:
					System.out.print("Enter a stock symbol: ");
					String viewSymbol = in.next().toUpperCase();
					Stock viewStock = YahooFinance.get(viewSymbol);
					viewStock.print();
					break;
				case 9:
					System.out.println("Simulating Market Movement...");
					p.moveMarket();
					break;
			};
			System.out.println();
		}
	}
	
	public static Stock stock(String symbol){
		return YahooFinance.get(symbol);
	}
	
	public static BigDecimal percent(BigDecimal a, BigDecimal b){
		return (a.subtract(b)).divide(a, 2, RoundingMode.HALF_UP).multiply(new BigDecimal(100));
	}
	
}
