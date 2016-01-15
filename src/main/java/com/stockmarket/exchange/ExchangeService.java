package com.stockmarket.exchange;

import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.stockmarket.model.Stock;
import com.stockmarket.model.StockType;

/*
 * This class fetch data from stock exchange and also update the tricker price of stocks.
 * @author Rohit Kumar
 */
@Service
public class ExchangeService implements IExchangeService{

	private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeService.class);
	
	private static final ConcurrentHashMap<String, Stock> GBCE_EXCHANGE_DATA;
	static {
		GBCE_EXCHANGE_DATA = new ConcurrentHashMap<String, Stock>();
		GBCE_EXCHANGE_DATA.put("TEA", new Stock("TEA", StockType.COMMON, 0.0, 0.0, 100.0));
		GBCE_EXCHANGE_DATA.put("POP", new Stock("POP", StockType.COMMON, 8.0, 0.0, 100.0));
		GBCE_EXCHANGE_DATA.put("ALE", new Stock("ALE", StockType.COMMON, 23.0, 0.0, 60.0));
		GBCE_EXCHANGE_DATA.put("GIN", new Stock("GIN", StockType.PREFERRED, 8.0, 2.0, 100.0));
		GBCE_EXCHANGE_DATA.put("JOE", new Stock("JOE", StockType.COMMON, 13.0, 0.0, 250.0));
	}
	
	public ExchangeService() {	}
	
	/*
	 * (non-Javadoc)
	 * @see com.stockmarket.exchange.IExchangeService#fetchDataFromExchange(com.stockmarket.exchange.ExchangeCode)
	 */
	@Override
	public ConcurrentHashMap<String, Stock> fetchDataFromExchange(ExchangeCode exchange) {
		LOGGER.debug("Fecthing Data from {} Stock Exchange. ",exchange.getName());
		ConcurrentHashMap<String, Stock> exchangeData = new ConcurrentHashMap<String, Stock>();
		exchangeData = loadData();
		LOGGER.debug("Returning Data for {} Stock Exchange. ",exchange.getName());
		return exchangeData;
	}
	
	private ConcurrentHashMap<String, Stock> loadData(){
		LOGGER.debug("Loding... Exchange Data into memory. ");
		/*
		 * Here we can load data from Exchange Repository.
		 */
		LOGGER.debug("Data Loding Completed. ");
		return GBCE_EXCHANGE_DATA;
	}

	/*
	 * (non-Javadoc)
	 * @see com.stockmarket.exchange.IExchangeService#updateStockTickerPrice(com.stockmarket.model.Stock)
	 */
	@Override
	public boolean updateStockTickerPrice(Stock stock) {
		LOGGER.debug("Stock {} updated Tricker Price is {}. ",stock.getStockSymbol(), stock.getTickerPrice());
		return this.GBCE_EXCHANGE_DATA.put(stock.getStockSymbol(), stock) != null;
	}

	
}