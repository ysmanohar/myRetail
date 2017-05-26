package com.target.model;

public class Price {
	private Double current_price;
    private String currency_code;
    
    public Price() {
		
	}
    
	public Price(Double current_price, String currency_code) {
		this.current_price = current_price;
		this.currency_code = currency_code;
	}
	
	public Double getCurrent_price() {
		return current_price;
	}
	public void setCurrent_price(Double current_price) {
		this.current_price = current_price;
	}
	public String getCurrency_code() {
		return currency_code;
	}
	public void setCurrency_code(String currency_code) {
		this.currency_code = currency_code;
	}
    
    
}
