package com.stockmarket.service;

import com.stockmarket.formula.FormulaRequest;
import com.stockmarket.helper.StockMarketException;
import com.stockmarket.model.Trade;
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
 * @IStockMarketService handles above mentioned requirements
 * @author Rohit Kumar
 */
public interface IStockMarketService {
	
	/*
	 * @param @FormulaRequest this method only supports request type @FormulaRequest.DividendYield and PERatio
	 * @param StockSymbol
	 * @param stock price
	 * @return formula result 
	 */
	Double executeFormulaRequest(FormulaRequest request, String stockSymbol, double price) throws StockMarketException;
	
	/*
	 * @param @FormulaRequest this method only supports request type @FormulaRequest.VolumeWeightedStockPrice
	 * @param StockSymbol
	 * @return formula result 
	 */
	Double executeFormulaRequest(FormulaRequest request, String stockSymbol) throws StockMarketException;
	
	/*
	 * @param @FormulaRequest this method only supports request type @FormulaRequest.GeometricMean
	 * @return formula result 
	 */
	Double executeFormulaRequest(FormulaRequest request) throws StockMarketException;
	
	/*
	 * @param @Trade.
	 * @return true and false.
	 */
	boolean recordTrade(Trade trade);
	
	/*
	 * @param stockSymbol
	 * @param tradedPrice
	 * @param shareQuantity
	 * @return true and false.
	 */
	boolean buyStock(String stockSymbol, double tradedPrice, long shareQuantity) throws StockMarketException;
	
	/*
	 * @param stockSymbol
	 * @param tradedPrice
	 * @param shareQuantity
	 * @return true and false.
	 */
	boolean sellStock(String stockSymbol, double tradedPrice, long shareQuantity) throws StockMarketException;

}
