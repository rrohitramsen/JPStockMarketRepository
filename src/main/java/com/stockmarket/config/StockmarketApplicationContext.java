package com.stockmarket.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.stockmarket.formula.FormulaRequest;
import com.stockmarket.helper.StockMarketException;
import com.stockmarket.service.StockMarketService;

@Configuration
@ComponentScan(basePackages = {
        "com.stockmarket.exchange",
		"com.stockmarket.service"
})
public class StockmarketApplicationContext {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StockmarketApplicationContext.class);
	
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
	 */
	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(StockmarketApplicationContext.class);
		StockMarketService stockMarket = context.getBean(StockMarketService.class);
		try {
			
			/*
			 *  [R] a[i] - For a given stock (type preferred), Given any price as input, calculate the dividend yield
			 */
			double result = stockMarket.executeFormulaRequest(FormulaRequest.DividendYield, "GIN", 85);
			LOGGER.debug("{} = {} ",FormulaRequest.DividendYield.name(),result);
			
			/*
			 *  [R] a[i] - For a given stock (type common), Given any price as input, calculate the dividend yield
			 */
			result = stockMarket.executeFormulaRequest(FormulaRequest.DividendYield, "ALE", 85);
			LOGGER.debug("{} = {} ",FormulaRequest.DividendYield.name(),result);
			
			/*
			 *  [R] a[ii] - For a given stock, Given any price as input, calculate the P/E Ratio
			 */
			result = stockMarket.executeFormulaRequest(FormulaRequest.PERatio, "ALE", 85);
			LOGGER.debug("{} = {} ",FormulaRequest.PERatio.name(),result);
			
			/*
			 *  [R] a[iii] - For a given stock, Record a trade, with timestamp, quantity of shares, 
			 *               buy or sell indicator and traded price
			 */
			boolean tradeComplete;
			tradeComplete = stockMarket.buyStock("ALE", 4, 1000);
			LOGGER.debug("Record Trade with BUY indicator complete : {} ",tradeComplete);
			
			tradeComplete = stockMarket.sellStock("JOE", 4, 1000);
			LOGGER.debug("Record Trade with SELL indicator complete : {}",tradeComplete);
			
			
			/*
			 *  [R] a[iv] - For a given stock, Calculate Volume Weighted Stock Price based on trades in past 15 minutes
			 *  
			 */
			
			result = stockMarket.executeFormulaRequest(FormulaRequest.VolumeWeightedStockPrice, "ALE");
			LOGGER.debug("{} Based on trades in past 15 minutes = {} ",FormulaRequest.VolumeWeightedStockPrice.name(),result);
			
			/*
			 *  [R] b - Calculate the GBCE All Share Index using the geometric mean of prices for all stocks
			 *  
			 */
			result = stockMarket.executeFormulaRequest(FormulaRequest.GeometricMean);
			LOGGER.debug("{} The GBCE All Share Index using the geometric mean of prices for all stocks = {} ",FormulaRequest.GeometricMean.name(),result);
			
			
		} catch (StockMarketException e) {
			e.printStackTrace();
		}
	}

}
