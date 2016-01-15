package com.stockmarket.exchange;

import java.util.concurrent.ConcurrentHashMap;

import com.stockmarket.model.Stock;

/*
 * @author Rohit Kumar
 */
public interface IExchangeService {
	
	/**
     * @param @ExchangeCode Stock exchange code
     * @return In-Memory data of Stock Exchange
     */
	ConcurrentHashMap<String, Stock> fetchDataFromExchange(ExchangeCode exchange);
	
	/*
	 * /**
     * @param @Stock ticker price to be updated
     * @return boolean type result true of false
     */
	boolean updateStockTickerPrice(Stock buyStock);
}
