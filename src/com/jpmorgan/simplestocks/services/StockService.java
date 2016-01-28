package com.jpmorgan.simplestocks.services;

import com.jpmorgan.simplestocks.data.Trade;
/**
 * 
 * @author saurabh_vaidya
 *
 */
public interface StockService {
	/**
	 * To calculate the Dividend Yield
	 * @param stockSymbol Stock Symbol
	 * @return double value for dividend yield
	 * @throws Exception
	 */
	public double calculateDividendYield(String stockSymbol) throws Exception;
	/**
	 * To calculate PE Ratio
	 * @param stockSymbol
	 * @return double value for PE Ratio
	 * @throws Exception
	 */
	public double calculatePERatio(String stockSymbol) throws Exception;
	/**
	 * To calculate the Recorded Trade
	 * @param trade
	 * @return true/false if trade is recorded
	 * @throws Exception
	 */
	public boolean recordTrade(Trade trade) throws Exception;
	/**
	 * To calculate Stock Price
	 * @param stockSymbol
	 * @return double value for Stock PRice
	 * @throws Exception
	 */
	public double calculateStockPrice(String stockSymbol) throws Exception;
	/**
	 * Calculates GBCE for All Share Index which is greater than 0
	 * @return double value
	 * @throws Exception
	 */
	public double calculateGBCEAllShareIndex() throws Exception;
}
