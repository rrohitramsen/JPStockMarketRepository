package com.stockmarket.model;

/*
 * @author Rohit Kumar
 * Stock Entity class
 */
public class Stock {
	
	private String stockSymbol;
	private StockType type = StockType.COMMON;
	private Double lastDividend;
	private Double fixedDividen;
	private Double parValue;
	private Double tickerPrice;
	
	public Stock() {
	}
	
	public Stock(String stockSymbol, StockType type, Double lastDividend,
			Double fixedDividen, Double parValue) {
		this(stockSymbol, type, lastDividend, fixedDividen, parValue, 0.0);
	}
	public Stock(String stockSymbol, StockType type, Double lastDividend,
			Double fixedDividen, Double parValue, Double tickerPrice) {
		super();
		this.stockSymbol = stockSymbol;
		this.type = type;
		this.lastDividend = lastDividend;
		this.fixedDividen = fixedDividen;
		this.parValue = parValue;
		this.tickerPrice = tickerPrice;
	}


	public String getStockSymbol() {
		return stockSymbol;
	}

	public void setStockSymbol(String stockSymbol) {
		this.stockSymbol = stockSymbol;
	}

	public Double getParValue() {
		return parValue;
	}

	public void setParValue(Double parValue) {
		this.parValue = parValue;
	}

	public Double getTickerPrice() {
		return tickerPrice;
	}

	public void setTickerPrice(Double tickerPrice) {
		this.tickerPrice = tickerPrice;
	}
	
	public StockType getType() {
		return type;
	}

	public void setType(StockType type) {
		this.type = type;
	}

	public Double getLastDividend() {
		return lastDividend;
	}

	public void setLastDividend(Double lastDividend) {
		this.lastDividend = lastDividend;
	}

	public Double getFixedDividen() {
		return fixedDividen;
	}

	public void setFixedDividen(Double fixedDividen) {
		this.fixedDividen = fixedDividen;
	}
	
	@Override
    public String toString() {
		return "Stock [ Stock Symbol = "+this.stockSymbol
				+ ", Type = "+this.type
				+ ", Last Dividend = "+this.lastDividend
				+ ", Fixed Dividend = "+this.fixedDividen
				+ ", Par Value = "+this.parValue
				+ ", Ticker Price = "+this.tickerPrice
				+ " ]";
    }

	
}
