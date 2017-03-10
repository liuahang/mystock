package com.ahang.stock;

import java.util.ArrayList;
import java.util.List;

public class StockInfo {
    //代码
	private String symbol;
	private List<StockObj> chartlist = new ArrayList<StockObj>();
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public List<StockObj> getChartlist() {
		return chartlist;
	}
	public void setChartlist(List<StockObj> chartlist) {
		this.chartlist = chartlist;
	}
	
	
}
