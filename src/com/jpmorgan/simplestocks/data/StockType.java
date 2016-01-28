package com.jpmorgan.simplestocks.data;
/**
 * 
 * @author Saurabh Vaidya
 *
 */
public enum StockType {
	COMMON,//Stock is common and dividend yield is calculated with last dividend.
	PREFERRED //Stock is preferred and dividend yield is calculated with fixed dividend.
}
