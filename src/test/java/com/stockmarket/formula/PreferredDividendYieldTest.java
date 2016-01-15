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
public class PreferredDividendYieldTest {

	@Autowired
	private PreferredDividendYield preferredDividendYield;
	
	@Test
	public void testPreferredDividendYield() {
		assertEquals( 
                "class com.stockmarket.formula.PreferredDividendYield", 
                this.preferredDividendYield.getClass().toString());
	}
	
	@Test
	public void testPreferredDividendYieldFormula(){
		PreferredDividendYield preferredDividendYield = new PreferredDividendYield(50, 100, 10);
		double actual = preferredDividendYield.execute();
		double expected = ((preferredDividendYield.getFixedDividend()/100)*preferredDividendYield.getParValue())/preferredDividendYield.getPrice();
		assertEquals(expected, actual, 0.0);
	}
}
