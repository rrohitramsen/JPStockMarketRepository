package com.stockmarket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.stockmarket.entity.manager.StockEntityManager;
import com.stockmarket.exchange.ExchangeService;
import com.stockmarket.formula.CommonDividendYield;
import com.stockmarket.formula.GeometricMean;
import com.stockmarket.formula.PERatio;
import com.stockmarket.formula.PreferredDividendYield;
import com.stockmarket.formula.VolumeWeightedStockPrice;
import com.stockmarket.service.StockMarketService;

@Configuration
public class StockMarketJunitConfig {
	
	@Bean
	public CommonDividendYield commonDividendYield(){
		return new CommonDividendYield(0.0, 0.0);
	}
	
	@Bean
	public PreferredDividendYield referredDividendYield(){
		return new PreferredDividendYield(0, 0, 0);
	}
	
	@Bean
	public PERatio peRatio(){
		return new PERatio(0, 0);
	}
	
	@Bean
	public GeometricMean geometricMean(){
		return new GeometricMean(null, 0);
	}
	
	@Bean
	public VolumeWeightedStockPrice volumeWeightedStockPrice(){
		return new VolumeWeightedStockPrice(0, 0);
	}
	
	@Bean
	public StockEntityManager stockEntityManager(){
		return StockEntityManager.getInstance();
	}
	
	@Bean
	public ExchangeService exchangeService(){
		return new ExchangeService();
	}
	
	@Bean
	public StockMarketService stockMarketService(){
		return new StockMarketService(exchangeService());
	}
	

}
