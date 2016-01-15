package com.stockmarket.formula;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * /*
 * This class calculates the DividendYield for stock type Preferred
 * @author Rohit Kumar
 */
public class PreferredDividendYield implements Formula<Double>{

	private static final Logger LOGGER = LoggerFactory.getLogger(PreferredDividendYield.class);
	
	private final double fixedDividend;
	private final double parValue;
	private final double price;
	
	public PreferredDividendYield(double fixedDividend, double parValue, double price) {
		super();
		this.fixedDividend = fixedDividend;
		this.parValue = parValue;
		this.price = price;
	}

	@Override
	public final Double execute() {
		
		LOGGER.debug("For a given stock of type Preferred and price, Calculating Dividend Yield = (fixedDividend/100) * parValue / price = {}/{} * {} / {}", fixedDividend,100,parValue, price);
		
		Double result = (((this.fixedDividend)/(100.0) * this.parValue)/this.price); 
		return result;
	}

	public double getFixedDividend() {
		return fixedDividend;
	}

	public double getParValue() {
		return parValue;
	}

	public double getPrice() {
		return price;
	}

}
