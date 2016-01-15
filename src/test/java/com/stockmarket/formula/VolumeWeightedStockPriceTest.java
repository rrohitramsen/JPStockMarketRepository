package com.stockmarket.formula;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.stockmarket.config.StockMarketJunitConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = StockMarketJunitConfig.class, loader=AnnotationConfigContextLoader.class)
public class VolumeWeightedStockPriceTest {
	
	@Autowired
	private VolumeWeightedStockPrice volumeWeightedStockPrice;
	
	@Test
	public void testVolumeWeightedStockPrice() {
		assertEquals( 
                "class com.stockmarket.formula.VolumeWeightedStockPrice", 
                this.volumeWeightedStockPrice.getClass().toString());
	}
	
	@Test
	public void testVolumeWeightedStockPriceFormula(){
		VolumeWeightedStockPrice volumeWeightedStockPrice = new VolumeWeightedStockPrice( 20.0, 2);
		double actual = volumeWeightedStockPrice.execute();
		double expected = volumeWeightedStockPrice.getVolumeWeightedStockPrice()/volumeWeightedStockPrice.getTotalQuantity();
		assertEquals(expected, actual, 0.0);
	}

}
