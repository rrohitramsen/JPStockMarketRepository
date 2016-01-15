package com.stockmarket.service;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stockmarket.entity.manager.IStockEntityManager;
import com.stockmarket.entity.manager.StockEntityManager;
import com.stockmarket.exchange.ExchangeCode;
import com.stockmarket.exchange.IExchangeService;
import com.stockmarket.formula.Formula;
import com.stockmarket.formula.FormulaRequest;
import com.stockmarket.formula.factory.FormulaFactory;
import com.stockmarket.helper.StockMarketException;
import com.stockmarket.model.Stock;
import com.stockmarket.model.Trade;
import com.stockmarket.model.TradeIndicator;
/*
 * Requirements :
 * Provide working source code that will :-
 * [R]a. For a given stock,
 *		i. Given any price as input, calculate the dividend yield
 * 		ii. Given any price as input, calculate the P/E Ratio
 * 		iii. Record a trade, with timestamp, quantity of shares, buy or sell indicator and
 * 			traded price
 * 		iv. Calculate Volume Weighted Stock Price based on trades in past 15 minutes
 * [R]b. Calculate the GBCE All Share Index using the geometric mean of prices for all stocks
 * 
 * @StockMarketService handles above mentioned requirements
 * @author Rohit Kumar
 */
@Service
public class StockMarketService implements IStockMarketService{

	private static final Logger LOGGER = LoggerFactory.getLogger(StockMarketService.class);
	private static final IStockEntityManager STOCK_ENTITY_MANAGER = StockEntityManager.getInstance();
	private static final double STOCK_PRICE_CHECK = 0.0;
	
	private IExchangeService exchangeService;
	private final ConcurrentHashMap<String, Stock> EXCHANGE_DATA;
	
	@Autowired
	public StockMarketService(IExchangeService exchangeService) {
		this.exchangeService = exchangeService;
		EXCHANGE_DATA = this.exchangeService.fetchDataFromExchange(ExchangeCode.GBCE);
	}
	
	/*
	 * @param @FormulaRequest this method only supports request type @FormulaRequest.DividendYield and PERatio
	 * @param StockSymbol
	 * @param stock price
	 * @return formula result 
	 */
	@Override
	public Double executeFormulaRequest(FormulaRequest request, String stockSymbol, double price) throws StockMarketException {
		
		LOGGER.debug("Stock Market Calculating formula {} for Stock {} with price {}", request.name(), stockSymbol, price);
		
		Formula<Double> formula ;
		Stock stock = EXCHANGE_DATA.get(stockSymbol);
		if (stock == null || !(price > STOCK_PRICE_CHECK)){
			throw new StockMarketException("Stock ["+stockSymbol+"] is not listed on the exchange.");
			
		}else {
			formula = FormulaFactory.newFormula(request, stock, price);
		} 
			
		LOGGER.debug("Formula created {} ", formula);
		Double result = (Double) formula.execute();
		
		LOGGER.debug("Formula {} return the result {} ", request.name(), result);
		return result;
	}
	
	/*
	 * @param @FormulaRequest this method only supports request type @FormulaRequest.VolumeWeightedStockPrice
	 * @param StockSymbol
	 * @return formula result 
	 */
	public Double executeFormulaRequest(FormulaRequest request, String stockSymbol) throws StockMarketException {
			
		LOGGER.debug("Stock Market Calculating formula {} for Stock {} ", request.name(), stockSymbol);
		
		Formula<Double> formula ;
		Stock stock = EXCHANGE_DATA.get(stockSymbol);
		if (stock == null){
			throw new StockMarketException("Stock ["+stockSymbol+"] is not listed on the exchange.");
			
		}else {
			formula = FormulaFactory.newFormula(request, stock);
		} 
		
		LOGGER.debug("Formula created {} ", formula);
		Double result = (Double) formula.execute();
		
		LOGGER.debug("Formula {} return the result {} ", request.name(), result);
		return result;
	}
	
	/*
	 * @param @FormulaRequest this method only supports request type @FormulaRequest.GeometricMean
	 * @return formula result 
	 */
	public Double executeFormulaRequest(FormulaRequest request) throws StockMarketException {
		
		LOGGER.debug("Stock Market Calculating formula {} ", request.name());
		
		Formula<Double> formula = FormulaFactory.newFormula(request, EXCHANGE_DATA);
		
		LOGGER.debug("Formula created {} ", formula);
		Double result = (Double) formula.execute();
		
		LOGGER.debug("Formula {} return the result {} ", request.name(), result);
		return result;
	}
	
	/*
	 * @param @Trade 
	 * @return True of False
	 * This method record trade in in-memory trade store.
	 * @see com.stockmarket.service.IStockMarketService#recordTrade(com.stockmarket.model.Trade)
	 */
	@Override
	public boolean recordTrade(Trade trade) {
		LOGGER.debug("Stock Market storing Trade {} in trade store", trade);
		boolean complete = STOCK_ENTITY_MANAGER.recordTrade(trade);
		if (complete) {
			LOGGER.debug("Trade {} with {} Indicator COMPLETE",trade, trade.getTradeIndicator().name());
		}else {
			LOGGER.debug("Trade {} with {} Indicator NOT-COMPLETE",trade, trade.getTradeIndicator().name());
		}
		return complete;
	}

	
	/*
	 * This method perform Trade with BUY Indication
	 * @see com.stockmarket.service.IStockMarketService#buyStock(java.lang.String, double, long)
	 */
	@Override
	public boolean buyStock(String stockSymbol, double tradedPrice, long shareQuantity) throws StockMarketException {
		LOGGER.debug("Buy Stock {} with Price {} in ShareQuantity {} ", stockSymbol, tradedPrice, shareQuantity);
		
		Stock buyStock = EXCHANGE_DATA.get(stockSymbol);
		boolean tradeComplete = false;
		if (buyStock != null && tradedPrice > STOCK_PRICE_CHECK){
			buyStock.setTickerPrice(tradedPrice);
			Trade trade = new Trade(buyStock, new Date(), tradedPrice, shareQuantity, TradeIndicator.BUY);
			tradeComplete = this.recordTrade(trade);
		}else {
			throw new StockMarketException("Stock ["+stockSymbol+"] is not listed on the exchange.");
		}
		
		if (tradeComplete){
			exchangeService.updateStockTickerPrice(buyStock);
		}
		return tradeComplete;
	}

	/*
	 * This method perform Trade with SELL Indication
	 * @see com.stockmarket.service.IStockMarketService#sellStock(java.lang.String, double, long)
	 */
	@Override
	public boolean sellStock(String stockSymbol, double tradedPrice, long shareQuantity) throws StockMarketException {
		LOGGER.debug("Sell Stock {} with Price {} in ShareQuantity {} ", stockSymbol, tradedPrice, shareQuantity);
		
		Stock sellStock = EXCHANGE_DATA.get(stockSymbol);
		boolean tradeComplete = false;
		if (sellStock != null && tradedPrice > STOCK_PRICE_CHECK) {
			sellStock.setTickerPrice(tradedPrice);
			Trade trade = new Trade(sellStock, new Date(), tradedPrice, shareQuantity, TradeIndicator.SELL);
			tradeComplete = this.recordTrade(trade);
		}else {
			throw new StockMarketException("Stock ["+stockSymbol+"] is not listed on the exchange.");
		}
		if (tradeComplete){
			exchangeService.updateStockTickerPrice(sellStock);
		}
		return tradeComplete;
	}

}
