package com.stockmarket.formula.factory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stockmarket.entity.manager.IStockEntityManager;
import com.stockmarket.entity.manager.StockEntityManager;
import com.stockmarket.formula.CommonDividendYield;
import com.stockmarket.formula.Formula;
import com.stockmarket.formula.FormulaRequest;
import com.stockmarket.formula.GeometricMean;
import com.stockmarket.formula.PERatio;
import com.stockmarket.formula.PreferredDividendYield;
import com.stockmarket.formula.VolumeWeightedStockPrice;
import com.stockmarket.helper.StockMarketException;
import com.stockmarket.model.Stock;
import com.stockmarket.model.StockType;
import com.stockmarket.model.Trade;

/*
 * Formula Factory creates new formula for given parameters.
 * @auhtor Rohit Kumar
 */
public class FormulaFactory {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FormulaFactory.class);
	private static final IStockEntityManager STOCK_ENTITY_MANAGER = StockEntityManager.getInstance(); 
	
	/*
	 * @param @FormulaRequest this method only supports request type @FormulaRequest.DividendYield and PERatio
	 * @param @Stock
	 * @param stock price
	 * @return @Formula 
	 */
	public static Formula<Double> newFormula(FormulaRequest request, Stock stock, double price) throws StockMarketException {
		
		Formula<Double> formula;
		if (request.equals(FormulaRequest.DividendYield)){
			formula = FormulaFactory.newDividendYield(stock, price);
			
		}else if (request.equals(FormulaRequest.PERatio)){
			formula = FormulaFactory.newPERatio(stock, price);
			
		}else{
			throw new StockMarketException(request+"**Not Supported**");
		}
		return formula;
	}
	
	/*
	 * @param @FormulaRequest this method only supports request type @FormulaRequest.VolumeWeightedStockPrice
	 * @param @Stock
	 * @return @Formula 
	 */
	public static Formula<Double> newFormula(FormulaRequest request, Stock stock) throws StockMarketException {

		Formula<Double> formula;
		if (request.equals(FormulaRequest.VolumeWeightedStockPrice)){
			formula = FormulaFactory.newVolumeWeightedStockPrice(STOCK_ENTITY_MANAGER.getTrades(), stock);
			
		}else{
			throw new StockMarketException(request+"**Not Supported**");
		}
		
		return formula;
	}
	
	/*
	 * @param @FormulaRequest this method only supports request type @FormulaRequest.GeometricMean
	 * @param Exchange Data
	 * @return @Formula 
	 */
	public static Formula<Double> newFormula(FormulaRequest request, ConcurrentHashMap<String, Stock> exchangeData) throws StockMarketException {
		
		Formula<Double> formula ;
		if (request.equals(FormulaRequest.GeometricMean)){
			formula = FormulaFactory.newGeometricMean(exchangeData);
			
		}else{
			throw new StockMarketException(request+"**Not Supported**");
		}
		return formula;
	}

	public static Formula<Double> newPERatio(Stock stock, double price) {
		LOGGER.debug("Creating P/E Ration Formula for Stock: {} and Price: {} ",stock, price);
		
		Formula<Double> formula = new PERatio(price, stock.getLastDividend());
		LOGGER.debug("P/E Ration Formula has been created {} ",formula);
		return formula;
	}

	public static Formula<Double> newDividendYield(Stock stock, double price) {
		LOGGER.debug("Creating DividendYield Formula for Stock: {} and Price: {} ",stock, price);
		
		Formula<Double> formula;
		if (stock.getType().equals(StockType.COMMON)){
			formula = new CommonDividendYield(stock.getLastDividend(), price);
		}else{
			formula = new PreferredDividendYield(stock.getFixedDividen(), stock.getParValue(), price);
		}
		
		LOGGER.debug("DividendYield Formula has been created {} ",formula);
		return formula;
	}
	
	public static Formula<Double> newVolumeWeightedStockPrice(TreeMap<Date, Trade> trades, Stock stock) {
		LOGGER.debug("Creating VolumeWeightedStockPrice Formula for Stock:{} ",stock);
		
		Date now = new Date();
		//Date 15 minutes ago
		Date startTime = new Date(now.getTime()- 15*60*1000);
		SortedMap<Date, Trade> tradesIn15Min = trades.tailMap(startTime);
		// Calculate 
		Double volumeWeigthedStockPrice = 0.0;
		Long totalQuantity = 0L;
		for (Trade trade: tradesIn15Min.values()) {
			/*
			 * Filter out the trades of the given stock.
			 */
			if (trade.getStock().getStockSymbol().equals(stock.getStockSymbol())) {
				totalQuantity += trade.getSharesQuantity();
				volumeWeigthedStockPrice += trade.getTradedPrice() * trade.getSharesQuantity();
			}
		} 
		
		Formula<Double> formula = new VolumeWeightedStockPrice(volumeWeigthedStockPrice, totalQuantity);
		LOGGER.debug("VolumeWeightedStockPrice Formula has been created {} ",formula);
		return formula;
	}

	public static Formula<Double> newGeometricMean(ConcurrentHashMap<String, Stock> exchangeData) {
		LOGGER.debug("Creating GeometricMean Formula");
		
		Collection<Stock> stocks = exchangeData.values();
		List<Double> priceList = new ArrayList<Double>(stocks.size());
		int count=0;
		for (Stock stock : stocks) {
			if (stock.getTickerPrice() > 0.0){
				priceList.add(stock.getTickerPrice());
				count++;
			}
		}
		Formula<Double> formula = new GeometricMean(priceList, count);
		LOGGER.debug("GeometricMean Formula has been created {} ",formula);
		return formula;
	}


}
