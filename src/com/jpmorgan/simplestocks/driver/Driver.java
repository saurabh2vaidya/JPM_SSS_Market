package com.jpmorgan.simplestocks.driver;

import java.util.ArrayList;
import java.util.HashMap;
import com.jpmorgan.simplestocks.data.Stock;
import com.jpmorgan.simplestocks.data.StockType;
import com.jpmorgan.simplestocks.data.Trade;
import com.jpmorgan.simplestocks.data.TradeIndicator;
import com.jpmorgan.simplestocks.driver.utils.DateUtils;
import com.jpmorgan.simplestocks.helper.StocksEntityHelper;
import com.jpmorgan.simplestocks.helper.impl.StocksEntityHelperImpl;
import com.jpmorgan.simplestocks.services.StockService;
import com.jpmorgan.simplestocks.services.impl.StockServiceImpl;
/**
 * Driver program to test the below:
 * a.Dividend Yield
 * b.PE Ratio
 * c.Record a trade
 * d.Volume weighted Stock price
 * e.GBCE All Trade Index
 * @author saurabh_vaidya
 *
 */
public class Driver {

	public static void main(String[] args) {

		Stock stTEA = new Stock();
		stTEA.setStockSymbol("TEA");
		stTEA.setStockType(StockType.COMMON);
		stTEA.setLastDividend(4);
		stTEA.setFixedDividend(0);
		stTEA.setParValue(100);

		Stock stPOP = new Stock();
		stPOP.setStockSymbol("POP");
		stPOP.setStockType(StockType.COMMON);
		stPOP.setLastDividend(8);
		stPOP.setFixedDividend(0);
		stPOP.setParValue(100);

		Stock stALE = new Stock();
		stALE.setStockSymbol("ALE");
		stALE.setStockType(StockType.COMMON);
		stALE.setLastDividend(23);
		stALE.setFixedDividend(0);
		stALE.setParValue(60);

		Stock stGIN = new Stock();
		stGIN.setStockSymbol("GIN");
		stGIN.setStockType(StockType.PREFERRED);
		stGIN.setLastDividend(8);
		stGIN.setFixedDividend(0.02);
		stGIN.setParValue(100);

		Stock stJOE = new Stock();
		stJOE.setStockSymbol("JOE");
		stJOE.setStockType(StockType.COMMON);
		stJOE.setLastDividend(13);
		stJOE.setFixedDividend(0);
		stJOE.setParValue(250);

		HashMap<String, Stock> stocks  = new HashMap<String, Stock>();
		stocks.put("TEA", stTEA);
		stocks.put("POP", stPOP);
		stocks.put("ALE", stALE);
		stocks.put("GIN", stGIN);
		stocks.put("JOE", stJOE);


		//Setting the list of Trades
		ArrayList <Trade> listTrades = new ArrayList<Trade>();

		Trade trade1 = new Trade();
		trade1.setTimeStamp(DateUtils.getNextMinutes(-30));
		trade1.setStock(stTEA);
		trade1.setTradeIndicator(TradeIndicator.SELL);
		trade1.setSharesQuantity(20);
		trade1.setTradePrice(11.34);

		Trade trade2 = new Trade();
		trade2.setTimeStamp(DateUtils.getNextMinutes(-28));
		trade2.setStock(stPOP);
		trade2.setTradeIndicator(TradeIndicator.BUY);
		trade2.setSharesQuantity(80);
		trade2.setTradePrice(4.34);

		Trade trade3 = new Trade();
		trade3.setTimeStamp(DateUtils.getNextMinutes(-26));
		trade3.setStock(stALE);
		trade3.setTradeIndicator(TradeIndicator.SELL);
		trade3.setSharesQuantity(210);
		trade3.setTradePrice(25.64);

		Trade trade4 = new Trade();
		trade4.setTimeStamp(DateUtils.getNextMinutes(-26));
		trade4.setStock(stGIN);
		trade4.setTradeIndicator(TradeIndicator.BUY);
		trade4.setSharesQuantity(80);
		trade4.setTradePrice(20.34);

		Trade trade5 = new Trade();
		trade5.setTimeStamp(DateUtils.getNextMinutes(-22));
		trade5.setStock(stJOE);
		trade5.setTradeIndicator(TradeIndicator.SELL);
		trade5.setSharesQuantity(20);
		trade5.setTradePrice(1.34);
		
		Trade trade7 = new Trade();
		trade7.setTimeStamp(DateUtils.getNextMinutes(-20));
		trade7.setStock(stGIN);
		trade7.setTradeIndicator(TradeIndicator.SELL);
		trade7.setSharesQuantity(20);
		trade7.setTradePrice(10.34);

		Trade trade6 = new Trade();
		trade6.setTimeStamp(DateUtils.getNextMinutes(-19));
		trade6.setStock(stGIN);
		trade6.setTradeIndicator(TradeIndicator.BUY);
		trade6.setSharesQuantity(5);
		trade6.setTradePrice(30.34);

		Trade trade8 = new Trade();
		trade8.setTimeStamp(DateUtils.getNextMinutes(-10));
		trade8.setStock(stPOP);
		trade8.setTradeIndicator(TradeIndicator.BUY);
		trade8.setSharesQuantity(70);
		trade8.setTradePrice(45.4);
		
		Trade trade9 = new Trade();
		trade9.setTimeStamp(DateUtils.getNextMinutes(-9));
		trade9.setStock(stALE);
		trade9.setTradeIndicator(TradeIndicator.SELL);
		trade9.setSharesQuantity(10);
		trade9.setTradePrice(10.34);

		Trade trade10 = new Trade();
		trade10.setTimeStamp(DateUtils.getNextMinutes(-2));
		trade10.setStock(stTEA);
		trade10.setTradeIndicator(TradeIndicator.BUY);
		trade10.setSharesQuantity(15);
		trade10.setTradePrice(65.66);
		
		listTrades.add(trade1);
		listTrades.add(trade2);
		listTrades.add(trade3);
		listTrades.add(trade4);
		listTrades.add(trade5);
		listTrades.add(trade6);
		listTrades.add(trade7);
		listTrades.add(trade8);
		listTrades.add(trade9);
		listTrades.add(trade10);
		

		StocksEntityHelper stocksEntityManager = new StocksEntityHelperImpl(stocks);
		StockService stockService= new StockServiceImpl(stocksEntityManager);

		//record Trade Test
		System.out.println("------------------------RECORD TRADE TEST-------------------------------");
		try{

			for(Trade trade: listTrades){
				boolean result = stockService.recordTrade(trade);
				System.out.println("Result for "+trade.getStock().getStockSymbol()+":"+result);
			}
			int tradesNumber = stocksEntityManager.getTrades().size();
			System.out.println("Trade Number:"+tradesNumber);
		}catch(Exception exception){
			System.out.println(exception);
		}
       //--------------------------------------------------------------------------------------------
		//Calculate Dividend Yield for stock symbols
		System.out.println("-------------------CALCULATE DIVIDEND YIELD------------------------------");
		String[] stockSymbols = {"TEA", "POP", "ALE", "GIN", "JOE"};
		try{
			for(String stockSymbol: stockSymbols){
				stockService.calculateDividendYield(stockSymbol);
			}
		}catch(Exception exception){
			System.out.println(exception);
		}
		//--------------------------------------------------------------------------------------------
		//Calculate PE Ratio stock symbols
		System.out.println("-------------------CALCULATE PE RATIO-----------------------------------");
		try{
			for(String stockSymbol: stockSymbols){
				stockService.calculatePERatio(stockSymbol);
			}
		}catch(Exception exception){
			System.out.println(exception);
		}
		//--------------------------------------------------------------------------------------------
		//Calculate Stock Price Test stock symbols
		System.out.println("-------------------CALCULATE STOCK PRICE----------------------------");
		try{
			for(String stockSymbol: stockSymbols){
				stockService.calculateStockPrice(stockSymbol);
			}
		}catch(Exception exception){
			System.out.println(exception);
		}
		//--------------------------------------------------------------------------------------------
		//Calculate GBCE All Share Index 
		System.out.println("----------------------CALCULATE GBCE SHARE INDEX---------------------");
		try{
			System.out.format("GBCE for all stocks is:: %.4f\n",stockService.calculateGBCEAllShareIndex());
		}catch(Exception exception){
			System.out.println(exception);
		}


	}


}
