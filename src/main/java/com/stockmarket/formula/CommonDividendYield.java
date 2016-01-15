package com.stockmarket.formula;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * This class calculates the DividendYield for stock type Common
 * @author Rohit Kumar
 */
public class CommonDividendYield implements Formula<Double>{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CommonDividendYield.class);
	
	private final double lastDividend;
	private final double price;
	
	public CommonDividendYield(Double lastDividend, Double price) { 
		this.lastDividend = lastDividend;
		this.price = price;
	}
	
	@Override
	public final Double execute() {
		LOGGER.debug("For a given stock of Type Common and price, Dividend Yield = lastDividend / price = {} / {}", lastDividend, price);
		double result = this.lastDividend/this.price;
		return result;
	}

	public final double getLastDividend() {
		return lastDividend;
	}

	public final double getPrice() {
		return price;
	}

}
