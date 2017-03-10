package com.ahang.stock;

public class StockObj {
	private double openPrice;
	private double highPrice;
	private double closePrice;
	private double lowPrice;
	private double ma30Price;
	private String time;
	/**
	 * "volume": 4532051618,
		"open": 2095.5835,
		"high": 2096.4009,
		"close": 1984.3948,
		"low": 1981.1725,
		"chg": -115.493,
		"percent": -5.5,
		"turnrate": 0.0,
		"ma5": 2091.247,
		"ma10": 2126.889,
		"ma20": 2146.391,
		"ma30": 2146.855,
		"dif": -23.28,
		"dea": -9.2,
		"macd": -28.15,
		"time": "Mon Dec 12 00:00:00 +0800 2016"
	 */
	
	
	public double getOpenPrice() {
		return openPrice;
	}
	public double getMa30Price() {
		return ma30Price;
	}
	public void setMa30Price(double ma30Price) {
		this.ma30Price = ma30Price;
	}
	public void setOpenPrice(double openPrice) {
		this.openPrice = openPrice;
	}
	public double getHighPrice() {
		return highPrice;
	}
	public void setHighPrice(double highPrice) {
		this.highPrice = highPrice;
	}
	public double getClosePrice() {
		return closePrice;
	}
	public void setClosePrice(double closePrice) {
		this.closePrice = closePrice;
	}
	public double getLowPrice() {
		return lowPrice;
	}
	public void setLowPrice(double lowPrice) {
		this.lowPrice = lowPrice;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	
}
