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

import com.stockmarket.formula.CommonDividendYield;
import com.stockmarket.formula.Formula;
import com.stockmarket.formula.GeometricMean;
import com.stockmarket.formula.PERatio;
import com.stockmarket.formula.PreferredDividendYield;
import com.stockmarket.formula.VolumeWeightedStockPrice;
import com.stockmarket.model.Stock;
import com.stockmarket.model.StockType;
import com.stockmarket.model.Trade;

/*
 * Formula Factory creates new formula for given parameters.
 * @auhtor Rohit Kumar
 */
public class FormulaFactory {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FormulaFactory.class);

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
