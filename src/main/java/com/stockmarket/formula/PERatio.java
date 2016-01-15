package com.stockmarket.formula;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * This class calculates the P/E Ration for the given stock and price.
 * author Rohit Kumar
 */
public class PERatio implements Formula<Double>{

	private static final Logger LOGGER = LoggerFactory.getLogger(PERatio.class);
	private final double price;
	private final double dividend;
	
	public PERatio(double price, double dividend) {
		this.price = price;
		this.dividend = dividend;
	}
	
	@Override
	public final Double execute() {
		LOGGER.debug("Calculating the P/E Ratio for the given stock and price, P/E Ratio = price / dividend = {} / {}", price, dividend);
		Double result = this.price/this.dividend;
		return result;
	}

	public double getPrice() {
		return price;
	}

	public double getDividend() {
		return dividend;
	}

}
