package com.jpmorgan.simplestocks.data;

import java.util.Date;
/**
 * 
 * @author Saurabh Vaidya
 *
 */
public class Trade {
	private Date timeStamp = null;
	private Stock stock = null;
	private TradeIndicator tradeIndicator = TradeIndicator.BUY;
	private int sharesQuantity = 0;
	private double tradePrice = 0.0;
	public Date getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	public Stock getStock() {
		return stock;
	}
	public void setStock(Stock stock) {
		this.stock = stock;
	}
	public TradeIndicator getTradeIndicator() {
		return tradeIndicator;
	}
	public void setTradeIndicator(TradeIndicator tradeIndicator) {
		this.tradeIndicator = tradeIndicator;
	}
	public int getSharesQuantity() {
		return sharesQuantity;
	}
	public void setSharesQuantity(int sharesQuantity) {
		this.sharesQuantity = sharesQuantity;
	}
	public double getTradePrice() {
		return tradePrice;
	}
	public void setTradePrice(double tradePrice) {
		this.tradePrice = tradePrice;
	}
	
	
}
