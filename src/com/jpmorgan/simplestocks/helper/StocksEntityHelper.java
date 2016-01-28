package com.jpmorgan.simplestocks.helper;

import java.util.ArrayList;
import java.util.HashMap;

import com.jpmorgan.simplestocks.data.Stock;
import com.jpmorgan.simplestocks.data.Trade;

/**
 * 
 * @author saurabh_vaidya
 *
 */
public interface StocksEntityHelper {
	/**
	 * To record a trade
	 * @param trade- Trade object to be passed
	 * @return - true/false if trade is recorded
	 * @throws Exception
	 */
	public boolean recordTrade(Trade trade) throws Exception;
	/**
	 * To get the trades
	 * @return
	 */
	public ArrayList<Trade> getTrades();
	/**
	 * To get Stock by passing stockSymbol
	 * @param stockSymbol
	 * @return
	 */
	public Stock getStockBySymbol(String stockSymbol);
	/**
	 * To get stocks
	 * @return
	 */
	public HashMap<String, Stock> getStocks();
}
