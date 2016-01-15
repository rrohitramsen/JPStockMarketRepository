package com.stockmarket.formula;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.stockmarket.config.StockMarketJunitConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = StockMarketJunitConfig.class, loader=AnnotationConfigContextLoader.class)
public class GeometricMeanTest {

	@Autowired
	private GeometricMean geometricMean;
	
	@Test
	public void testGeometricMean() {
		assertEquals( 
                "class com.stockmarket.formula.GeometricMean", 
                this.geometricMean.getClass().toString());
	}
	
	@Test
	public void testGeometricMeanFormula(){
		List<Double> prices = new ArrayList<Double>(2);
		prices.add(16.0);
		prices.add(16.0);
		GeometricMean geometricMean = new GeometricMean(prices, 2);
		double product = 16.0 * 16.0;
		double actual = geometricMean.execute();
		double expected = Math.pow(product, 1.0/2.0);
		assertEquals(expected, actual, 0.0);
	}

}
