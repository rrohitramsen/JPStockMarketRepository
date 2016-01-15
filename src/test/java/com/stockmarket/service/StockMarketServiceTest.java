package com.stockmarket.service;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.stockmarket.config.StockMarketJunitConfig;
import com.stockmarket.formula.FormulaRequest;
import com.stockmarket.helper.StockMarketException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = StockMarketJunitConfig.class, loader=AnnotationConfigContextLoader.class)
public class StockMarketServiceTest {
	
	@Autowired
	private StockMarketService stockMarketService;
	
	@Test
	public void testStockMarketService() {
		assertEquals( 
                "class com.stockmarket.service.StockMarketService", 
                this.stockMarketService.getClass().toString());
	}

	/*
	 *  Test [R] a[i] - For a given stock (type preferred), Given any price as input, calculate the dividend yield
	 */
	@Test
	public void testExecuteFormulaRequestPreferredDividendYield() throws StockMarketException{
		double actual = stockMarketService.executeFormulaRequest(FormulaRequest.DividendYield, "GIN", 85);
		double expected = 0.023529411764705882 ;
		assertEquals(expected, actual, 0.0);
		
	}
	
	/*
	 *  [R] a[i] - For a given stock (type common), Given any price as input, calculate the dividend yield
	 */
	@Test
	public void testExecuteFormulaRequestCommonDividendYield() throws StockMarketException{
		double actual = stockMarketService.executeFormulaRequest(FormulaRequest.DividendYield, "ALE", 85);
		double expected = 0.27058823529411763 ;
		assertEquals(expected, actual, 0.0);
		
	}

	
	/*
	 *  [R] a[ii] - For a given stock, Given any price as input, calculate the P/E Ratio
	 */
	@Test
	public void testExecuteFormulaRequestPERatio() throws StockMarketException{
		double actual = stockMarketService.executeFormulaRequest(FormulaRequest.PERatio, "JOE", 98);
		double expected = 7.538461538461538 ;
		assertEquals(expected, actual, 0.0);
		
	}
	
	/*
	 * Test StockMarketException for Unknown Stock
	 */
	@Test(expected = StockMarketException.class)
	public void testExecuteFormulaRequestExceptionStockUnknown() throws StockMarketException{
		stockMarketService.executeFormulaRequest(FormulaRequest.DividendYield, "UKNOWN", 85);
	}
	
	/*
	 * Test StockMarketException for Stock price 0.0
	 */
	@Test(expected = StockMarketException.class)
	public void testExecuteFormulaRequestExceptionStockPriceZero() throws StockMarketException{
		stockMarketService.executeFormulaRequest(FormulaRequest.DividendYield, "ALE", 0.0);
	}
	
	
	/*
	 *  [R] a[iii] - For a given stock, Record a trade, with timestamp, quantity of shares, 
	 *               buy or sell indicator and traded price
	 */
	@Test
	public void testBuyStock() throws StockMarketException{
		assertEquals(Boolean.TRUE, stockMarketService.buyStock("POP", 100.0, 989L));
	}
	
	
	/*
	 *  [R] a[iii] - For a given stock, Record a trade, with timestamp, quantity of shares, 
	 *               buy or sell indicator and traded price
	 */
	@Test
	public void testSellStock() throws StockMarketException{
		assertEquals(Boolean.TRUE, stockMarketService.sellStock("POP", 100.0, 989L));
	}
	
	/*
	 *  [R] a[iv] - For a given stock, Calculate Volume Weighted Stock Price based on trades in past 15 minutes
	 *  
	 */
	@Test
	public void testExecuteFormulaRequestVolumeWeightedStockPrice() throws StockMarketException{
		stockMarketService.sellStock("POP", 100.0, 989L);
		stockMarketService.sellStock("POP", 100.0, 989L);
		Double actual = stockMarketService.executeFormulaRequest(FormulaRequest.VolumeWeightedStockPrice, "POP");
		double expected = (100.0*989.0)/(989.0);
		assertEquals(expected, actual, 0.0);
		
	}
	
	/*
	 *  [R] b - Calculate the GBCE All Share Index using the geometric mean of prices for all stocks
	 *  
	 */
	@Test
	public void testExecuteFormulaRequestGeometricMean() throws StockMarketException{
		stockMarketService.sellStock("POP", 100.0, 989L);
		stockMarketService.sellStock("JOE", 44.0, 989L);
		stockMarketService.sellStock("GIN", 44.0, 989L);
		Double actual = stockMarketService.executeFormulaRequest(FormulaRequest.GeometricMean);
		double product = 100.0 * 44.0 * 44.0;
		double expected = Math.pow(product, 1.0/3.0);
		assertEquals(expected, actual, 0.0);
		
	}
	
	
	@Test(expected = StockMarketException.class)
	public void testBuyStockException() throws StockMarketException{
		assertEquals(Boolean.TRUE, stockMarketService.buyStock("UNKNOWN", 100.0, 989L));
	}
	
	@Test(expected = StockMarketException.class)
	public void testSellStockException() throws StockMarketException{
		assertEquals(Boolean.TRUE, stockMarketService.sellStock("UNKNOWN", 100.0, 989L));
	}
}
