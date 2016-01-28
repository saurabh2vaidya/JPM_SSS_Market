package com.jpmorgan.simplestocks.helper.impl;

import java.util.ArrayList;
import java.util.HashMap;

import com.jpmorgan.simplestocks.data.Stock;
import com.jpmorgan.simplestocks.data.Trade;
import com.jpmorgan.simplestocks.helper.StocksEntityHelper;

/**
 * 
 * @author saurabh_vaidya
 *
 */
public class StocksEntityHelperImpl implements StocksEntityHelper {
	private HashMap<String, Stock> stocks = null;
	private ArrayList<Trade> trades = null;
	/**
	 * 
	 * @param stocks
	 */
	public StocksEntityHelperImpl(HashMap<String, Stock> stocks){
		this.trades = new ArrayList<Trade>();
		this.stocks = new HashMap<String, Stock>();
		this.stocks = stocks;
	}
	/**
	 * 
	 */
	public boolean recordTrade(Trade trade) throws Exception {
		boolean result = false;
		try{
			result = trades.add(trade);
		}catch(Exception exception){
			throw new Exception("An error has occurred recording a trade in the system's backend.", exception);
		}
		return result;
	}
	
	public ArrayList<Trade> getTrades() {
		return trades;
	}
	public void setTrades(ArrayList<Trade> trades) {
		this.trades = trades;
	}
	public int getTradesNumber() {
		return trades.size();
	}
	/**
	 * 
	 */
	public Stock getStockBySymbol(String stockSymbol) {
		Stock stock = null;
		try{
			if(stockSymbol!=null){
				stock = stocks.get(stockSymbol);
			}
		}catch(Exception exception){
			try {
				throw new Exception ("An error has occurred recovering the stock object for the stock symbol: "+stockSymbol+".", exception);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return stock;
	}
	/**
	 * 
	 * @param stocks
	 */
	public void setStocks(HashMap<String, Stock> stocks) {
		this.stocks = stocks;
	}
	/**
	 * 
	 */
	public HashMap<String, Stock> getStocks() {
		return stocks;
	}

}
