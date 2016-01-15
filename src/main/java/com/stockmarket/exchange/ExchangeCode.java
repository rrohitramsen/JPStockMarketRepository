package com.stockmarket.exchange;

/*
 * @author Exchange Codes
 */
public enum ExchangeCode {
	
	GBCE("Global Beverage Corporation Exchange");
	
	String name;
	
	private ExchangeCode(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
