package com.stockmarket.formula;

/*
 * This enum defines the type of formula supported by this stock market application.
 */
public enum FormulaRequest {
	DividendYield ("Dividend Yield"),
	PERatio ("P/E Ratio"),
	GeometricMean ("Geometric Mean"),
	VolumeWeightedStockPrice ("Volume Weighted Stock Price "); 
	
	String name;
	private FormulaRequest(String name){
		this.name = name;
	}
		
}
