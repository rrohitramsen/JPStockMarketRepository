package com.stockmarket.model;

import java.util.Date;

/*
 * @author Rohit Kumar
 * Trade Entity class
 */
public class Trade {
	
	private Stock stock;
	private Date timeStamp;
	private Double tradedPrice;
	private Long sharesQuantity;
	private TradeIndicator tradeIndicator = TradeIndicator.BUY;
	
	
	public Stock getStock() {
		return stock;
	}
	
	public Trade(Stock stock, Date timeStamp, Double tradedPrice,
			Long sharesQuantity, TradeIndicator tradeIndicator) {
		super();
		this.stock = stock;
		this.timeStamp = timeStamp;
		this.tradedPrice = tradedPrice;
		this.sharesQuantity = sharesQuantity;
		this.tradeIndicator = tradeIndicator;
		this.stock.setTickerPrice(tradedPrice);
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}
	
	public Date getTimeStamp() {
		return timeStamp;
	}
	
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	public Double getTradedPrice() {
		return tradedPrice;
	}
	
	public void setTradedPrice(Double tradedPrice) {
		this.tradedPrice = tradedPrice;
	}
	
	public Long getSharesQuantity() {
		return sharesQuantity;
	}
	
	public void setSharesQuantity(Long sharesQuantity) {
		this.sharesQuantity = sharesQuantity;
	}
	
	public TradeIndicator getTradeIndicator() {
		return tradeIndicator;
	}
	
	public void setTradeIndicator(TradeIndicator tradeIndicator) {
		this.tradeIndicator = tradeIndicator;
	}
	
	@Override
    public String toString() {
		return "Trade [ Stock = "+this.stock
				+ ", Time Stamp = "+this.timeStamp
				+ ", Traded Price = "+this.tradedPrice
				+ ", Shares Quantity = "+this.sharesQuantity
				+ ", Trade Indicator = "+this.tradeIndicator
				+ " ]";
    }
}
