package com.stockmarket.formula;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Calculate the GBCE All Share Index using the geometric mean of prices for all stocks
 * author Rohit Kumar
 */
public final class GeometricMean implements Formula<Double>{

	private static final Logger LOGGER = LoggerFactory.getLogger(GeometricMean.class);
	
	private final List<Double> priceList;
	private final int stockCount;
	
	public GeometricMean(List<Double> priceList, int stockCount) {
		this.priceList = priceList;
		this.stockCount = stockCount;
	}
	
	@Override
	public final Double execute() {
		LOGGER.debug("Calculating the All Share Index using the geometric mean of prices for all stocks");
		
		Double product = 1.0;
		for (Double price : this.priceList) {
			product = product * price;
		}
		double result = Math.pow(product, 1.0/this.stockCount);
		return result;
	}
	
	public final List<Double> getPriceList() {
		return priceList;
	}

	public final int getStockCount() {
		return stockCount;
	}

}
