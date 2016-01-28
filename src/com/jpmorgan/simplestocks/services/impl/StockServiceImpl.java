package com.jpmorgan.simplestocks.services.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;

import com.jpmorgan.simplestocks.data.Stock;
import com.jpmorgan.simplestocks.data.Trade;
import com.jpmorgan.simplestocks.helper.StocksEntityHelper;
import com.jpmorgan.simplestocks.services.StockService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.math3.stat.StatUtils;
/**
 * 
 * @author saurabh_vaidya
 *
 */
public class StockServiceImpl implements StockService {
	private StocksEntityHelper stocksEntityManager = null;
	public StockServiceImpl(StocksEntityHelper stocksEntityManager){
		this.stocksEntityManager = stocksEntityManager;
	}
	/**
	 * 
	 */
	public double calculateDividendYield(String stockSymbol) throws Exception {
		double dividendYield = -1.0;
		try{
			Stock stock = stocksEntityManager.getStockBySymbol(stockSymbol);
			//If stock not supported
			if(stock==null){
				throw new Exception("Stock symbol "+stockSymbol+" not supported !!");
			}

			// Catch for divide by zero as on-screen price cannot be 0
			if(stock.getOnScreenPrice() <= 0.0){
				throw new Exception("On Screen price for the stock "+stockSymbol+" should be greater than 0.");
			}
			dividendYield = stock.getDividendYield();

			System.out.format("Dividend Yield calculated for "+ stockSymbol+": %.4f\n",dividendYield);
		}catch(Exception exception){
			throw new Exception("An error has occurred recovering the stock object for the stock symbol: "+stockSymbol+".", exception);
		}
		return dividendYield;
	}
	/**
	 * 
	 */
	public double calculatePERatio(String stockSymbol) throws Exception {
		double peRatio = -1.0;
		try{
			Stock stock = stocksEntityManager.getStockBySymbol(stockSymbol);

			//If stock not supported
			if(stock==null){
				throw new Exception("Stock symbol "+stockSymbol+" not supported !!");
			}

			// Catch for divide by zero as on-screen price cannot be 0
			if(stock.getOnScreenPrice() <= 0.0){
				throw new Exception("On Screen price for the stock "+stockSymbol+" should be greater than 0.");
			}

			peRatio = stock.getPeRatio();

			System.out.format(" P/E Ratio calculated for "+stockSymbol+ ": %.4f\n",peRatio);

		}catch(Exception exception){
			throw new Exception("Error calculating P/E Ratio for the stock symbol: "+stockSymbol+".", exception);
		}
		return peRatio;
	}

	public boolean recordTrade(Trade trade) throws Exception {
		boolean recordResult = false;
		try{
			// Null check for trade object
			if(trade==null){
				throw new Exception("Trade object cannot be null.");
			}
			// Null check for stock object
			if(trade.getStock()==null){
				throw new Exception("Stock object cannot be null.");
			}
			// shares quantity should be greater than zero
			if(trade.getSharesQuantity()<=0){
				throw new Exception("Shares quantity in the trade to record should be greater than 0.");
			}
			// shares price should be greater than zero
			if(trade.getTradePrice()<=0.0){
				throw new Exception("Shares price should be greater than 0.");
			}
			recordResult = stocksEntityManager.recordTrade(trade);
			// Update the on-screen price for the stock
			if(recordResult){
				trade.getStock().setOnScreenPrice(trade.getTradePrice());
			}
		}catch(Exception exception){
			throw new Exception("Error when trying to record a trade.", exception);
		}
		return recordResult;
	}

	public double calculateStockPrice(String stockSymbol) throws Exception {
		double stockPrice = 0.0;

		try{
			Stock stock = stocksEntityManager.getStockBySymbol(stockSymbol);

			// If the stock is not supported the a exception is raised
			if(stock==null){
				throw new Exception("The stock symbol ["+stockSymbol+"] is not supported !!.");
			}
			stockPrice = calculateStockPriceinRange(stockSymbol, 15);
			System.out.format(" Stock Price calculated for "+stockSymbol+": %.4f\n",stockPrice);
		}catch(Exception exception){
			throw new Exception("Error calculating P/E Ratio : "+stockSymbol+".", exception);

		}


		return stockPrice;
	}
	public double calculateGBCEAllShareIndex() throws Exception {
		double allShareIndex = 0.0;
		
		// Calculate stock price for all stock in the system
		HashMap<String, Stock> stocks = stocksEntityManager.getStocks();
		ArrayList<Double> stockPrices = new ArrayList<Double>();
		for(String stockSymbol: stocks.keySet() ){
			double stockPrice = calculateStockPriceinRange(stockSymbol, 0);
			if(stockPrice > 0){
				stockPrices.add(stockPrice);
			}
		}
		
		if(stockPrices.size()>=1){
			double[] stockPricesArray = new double[stockPrices.size()];
			
			for(int i=0; i<=(stockPrices.size()-1); i++){
				stockPricesArray[i] = stockPrices.get(i).doubleValue();
			}
			// Calculates the GBCE All Share Index
			allShareIndex = StatUtils.geometricMean(stockPricesArray);
		}
		return allShareIndex;
	}
	/**
	 *
	 * @author saurabh_vaidya
	 *
	 */
	private class StockPredicate implements Predicate{
		private String stockSymbol = "";
		private Calendar dateRange = null;
		public StockPredicate(String stockSymbol, int minutesRange){
			this.stockSymbol = stockSymbol;
			if( minutesRange > 0 ){
				dateRange = Calendar.getInstance();
				dateRange.add(Calendar.MINUTE, -minutesRange);
				//System.out.format("Date Range: %tF %tT\n", dateRange.getTime(), dateRange.getTime());
			}
		}
		public boolean evaluate(Object tradeObj1) {
			Trade trade = (Trade) tradeObj1;
			boolean valueToBeInclusive = trade.getStock().getStockSymbol().equals(stockSymbol);
			if(valueToBeInclusive && dateRange != null){
				valueToBeInclusive = dateRange.getTime().compareTo(trade.getTimeStamp())<=0;
			}
			return valueToBeInclusive;
		}

	}
/**
 * 
 * @param stockSymbol
 * @param minutesRange
 * @return
 */
	private double calculateStockPriceinRange(String stockSymbol, int minutesRange) {
		double stockPrice = 0.0;
		Collection<Trade> trades = CollectionUtils.select(stocksEntityManager.getTrades(), new StockPredicate(stockSymbol, minutesRange));
		//System.out.println("Trades collected in ["+stockSymbol+","+minutesRange+"]: "+trades.size());
		// Calculate the summation
		double totalShareQuantity = 0.0;
		double totalTradePrice = 0.0;
		for(Trade trade : trades){
			// Calculate summation of Trade Price x Quantity
			totalTradePrice += (trade.getTradePrice() * trade.getSharesQuantity());
			// Calculate Total Quantity
			totalShareQuantity += trade.getSharesQuantity();
		}
		// Calculate the stock price
		if(totalShareQuantity > 0.0){
			stockPrice = totalTradePrice / totalShareQuantity;	
		}
		return stockPrice;
	}

	

}
