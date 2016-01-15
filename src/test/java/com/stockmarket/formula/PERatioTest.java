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
public class PERatioTest {

	@Autowired
	private PERatio peRatio;
	
	@Test
	public void testPERatio() {
		assertEquals( 
                "class com.stockmarket.formula.PERatio", 
                this.peRatio.getClass().toString());
	}
	
	@Test
	public void testPERatioFormula(){
		PERatio peRatio = new PERatio( 20.0, 2.0);
		double actual = peRatio.execute();
		double expected = peRatio.getPrice()/peRatio.getDividend();
		assertEquals(expected, actual, 0.0);
	}
}
