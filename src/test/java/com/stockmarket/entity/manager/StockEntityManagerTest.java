package com.stockmarket.entity.manager;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.stockmarket.config.StockMarketJunitConfig;
import com.stockmarket.model.Stock;
import com.stockmarket.model.StockType;
import com.stockmarket.model.Trade;
import com.stockmarket.model.TradeIndicator;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = StockMarketJunitConfig.class, loader=AnnotationConfigContextLoader.class)
public class StockEntityManagerTest {
	
	@Autowired
	private StockEntityManager stockEntityManager;

	@Test
	public void testStockEntityManager() {
		assertEquals( 
                "class com.stockmarket.entity.manager.StockEntityManager", 
                this.stockEntityManager.getClass().toString());
	}
	
	@Test
	public void testRecordTrade(){
		Stock stock = new Stock("POP", StockType.COMMON, 8.0, 0.0, 100.0);
		Trade trade = new Trade(stock, new Date(), 100.0, 1000L, TradeIndicator.BUY);
		assertEquals(Boolean.TRUE, stockEntityManager.recordTrade(trade));
	}
	
	@Test
	public void testGetTrades(){
		Assert.assertNotNull(stockEntityManager.getTrades());
	}
}
