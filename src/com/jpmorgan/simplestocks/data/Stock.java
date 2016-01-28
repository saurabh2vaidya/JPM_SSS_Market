package com.jpmorgan.simplestocks.data;

/**
 * 
 * @author Saurabh Vaidya
 *
 */
public class Stock {
	private String stockSymbol = null;
	private StockType stockType = StockType.COMMON;
	private double lastDividend = 0.0;
	private double fixedDividend = 0.0;
	private double parValue = 0.0;
	private double onScreenPrice = 0.0;
	
	public String getStockSymbol() {
		return stockSymbol;
	}
	public void setStockSymbol(String stockSymbol) {
		this.stockSymbol = stockSymbol;
	}
	public StockType getStockType() {
		return stockType;
	}
	public void setStockType(StockType stockType) {
		this.stockType = stockType;
	}
	public double getLastDividend() {
		return lastDividend;
	}
	public void setLastDividend(double lastDividend) {
		this.lastDividend = lastDividend;
	}
	public double getFixedDividend() {
		return fixedDividend;
	}
	public void setFixedDividend(double fixedDividend) {
		this.fixedDividend = fixedDividend;
	}
	public double getParValue() {
		return parValue;
	}
	public void setParValue(double parValue) {
		this.parValue = parValue;
	}
	
	public double getOnScreenPrice() {
		return onScreenPrice;
	}
	public void setOnScreenPrice(double onScreenPrice) {
		this.onScreenPrice = onScreenPrice;
		System.out.println("New price for Stock:: "+stockSymbol+": "+onScreenPrice);
	}
	/**
	 * Calculate the Dividend Yield
	 * @return
	 */
	public double getDividendYield() {
		double dividendYield = -1.0;
		if(onScreenPrice > 0.0){
			if( stockType==StockType.COMMON)
				dividendYield = lastDividend / onScreenPrice;
			else
				dividendYield = (fixedDividend * parValue ) / onScreenPrice;
		}
		return dividendYield;
	}
	/**
	 * Calculate the PE Ratio 
	 * @return
	 */
	public double getPeRatio() {
		double peRatio = -1.0;
		
		if(onScreenPrice > 0.0){
			peRatio = onScreenPrice / getDividendYield();	
		}
		return peRatio;
	}
	

}
