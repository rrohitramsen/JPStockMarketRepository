package com.stockmarket.formula;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/*
 * Calculate Volume Weighted Stock Price
 * @author Rohit Kumar
 */
public class VolumeWeightedStockPrice implements Formula<Double>{

	private static final Logger LOGGER = LoggerFactory.getLogger(VolumeWeightedStockPrice.class);
	private final double volumeWeightedStockPrice;
	private final long totalQuantity;
	
	public VolumeWeightedStockPrice(double volumeWeightedStockPrice,
			long totalQuantity) {
		super();
		this.volumeWeightedStockPrice = volumeWeightedStockPrice;
		this.totalQuantity = totalQuantity;
	}

	@Override
	public final Double execute() {
		LOGGER.debug("Calculating Volume Weighted Stock Price based on trades in past 15 minutes");
		Double result = this.volumeWeightedStockPrice/this.totalQuantity;
		return result;
	}

	public final double getVolumeWeightedStockPrice() {
		return volumeWeightedStockPrice;
	}

	public final long getTotalQuantity() {
		return totalQuantity;
	}

}
