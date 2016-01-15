package com.stockmarket.formula;
/*
 * @author Rohit Kumar
 */
public interface Formula<T> {
	
	/*
	 * @return T type result of formula execution.
	 */
	T execute();
}
