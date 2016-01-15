package com.stockmarket.formula;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.stockmarket.config.StockMarketJunitConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = StockMarketJunitConfig.class, loader=AnnotationConfigContextLoader.class)
public class CommonDividendYieldTest {

	@Autowired
	private CommonDividendYield commonDividendYield;
	
	@Test
	public void testCommonDividendYield() {
		assertEquals( 
                "class com.stockmarket.formula.CommonDividendYield", 
                this.commonDividendYield.getClass().toString());
	}
	
	@Test
	public void testCommonDividendYieldFormula(){
		CommonDividendYield commonDividendYield = new CommonDividendYield( 20.0, 2.0);
		double actual = commonDividendYield.execute();
		double expected = commonDividendYield.getLastDividend()/commonDividendYield.getPrice();
		assertEquals(expected, actual, 0.0);
	}
}
