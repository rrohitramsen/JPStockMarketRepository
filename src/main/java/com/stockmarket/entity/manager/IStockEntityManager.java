package com.stockmarket.entity.manager;

import java.util.Date;
import java.util.TreeMap;

import com.stockmarket.model.Trade;

/*
 * @author Rohit Kumar
 */
public interface IStockEntityManager {
	
	/**
     * @param @Trade
     * @return boolean true if trade stored in trade store otherwise false;.
     */
	public boolean recordTrade(Trade trade);
	
	/**
     * @return Map<@Date, @Trade> all trades stored in trade store
     */
	public TreeMap<Date, Trade> getTrades();
}
