package com.stockmarket.entity.manager;

import java.util.Date;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stockmarket.model.Trade;

/*
 * This Singleton class manage in-memory trade store.
 * @author Rohit Kumar
 */
public class StockEntityManager implements IStockEntityManager {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StockEntityManager.class);
	private TreeMap<Date, Trade> tradeStore;
	
	private StockEntityManager() {
		this.tradeStore = new TreeMap<Date, Trade>();
	}
	
	private static class InstanceHolder {
		private static final StockEntityManager INSTANCE = new StockEntityManager();
	}
	
	public static StockEntityManager getInstance(){
		return InstanceHolder.INSTANCE;
	}

	/*
	 * (non-Javadoc)
	 * @see com.stockmarket.entity.manager.IStockEntityManager#recordTrade(com.stockmarket.model.Trade)
	 */
	@Override
	public boolean recordTrade(Trade trade) {
		LOGGER.debug("Storing Trade {} in in-memory trade store. ",trade);
		boolean stored = false;
		synchronized(trade) {
			stored = this.tradeStore.put(trade.getTimeStamp(), trade) == null;
			if (stored){
				LOGGER.debug("Trade {} stored in In-Memory Trade Store at time {} ",trade, trade.getTimeStamp());
			}else {
				LOGGER.debug("Trade {} not stored in trade store. ",trade);
			}
		}
		return stored;
	}

	/*
	 * (non-Javadoc)
	 * @see com.stockmarket.entity.manager.IStockEntityManager#getTrades()
	 */
	@Override
	public TreeMap<Date, Trade> getTrades() {
		LOGGER.debug("Get Trades from trade store ");
		TreeMap<Date, Trade> copy = new TreeMap<Date, Trade>(tradeStore);
		return copy;
	}
	
}
