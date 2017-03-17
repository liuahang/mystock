package com.ahang.stock;

public class TestInterface {

	public static void main(String args[]){
		//https://xueqiu.com/v4/stock/quote.json?code=SZ300090&_=1489395459765
	  System.out.println(System.currentTimeMillis());	
	  System.out.println("1489395459765");	
	  
	  
	  StockService service = new StockService();
	  String token = service.getToken("liuahang@163.com", "ahang123457");
	  System.out.println("token==" + token);
	  String str = service.getNameFromCode(token, "sz300090");
	  System.out.println("str==" + str);
	}
}
