package com.ahang.stock;

public class StockMaObj {

	//低于均线的最大值
	private double lowMax;
	//高于均线的最大值
	private double highMax;
	private String time;
	private String symbole;
	private String name;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getLowMax() {
		return lowMax;
	}
	public void setLowMax(double lowMax) {
		this.lowMax = lowMax;
	}
	public double getHighMax() {
		return highMax;
	}
	public void setHighMax(double highMax) {
		this.highMax = highMax;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getSymbole() {
		return symbole;
	}
	public void setSymbole(String symbole) {
		this.symbole = symbole;
	}
	
	
}
