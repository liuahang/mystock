package com.ahang.stock;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ahang.utils.JsonUtils;

public class StockService {

	/**
	 * 获取雪球的登录token
	 * @param userName liuahang@163.com
	 * @param password ahang123457
	 * @return
	 */
	public String getToken(String userName,String password){
		String token = "";
		String loginurl = "https://xueqiu.com/user/login";
		Map<String,Object> para = new HashMap<String,Object>();
		para.put("username", userName);
		para.put("password",password);
	     token = StockUtil.getXieQiuToken(loginurl, para);
		//System.out.println("token==" + token);
		return token;
	}
	
	/**
	 * 获取某股票的价格信息
	 * @param token
	 * @param symbol
	 * @param begin
	 * @param end
	 * @return
	 */
	public String getInfo(String token,String symbol,String begin,String end){
		String str = "";
		StringBuffer uri = new StringBuffer("https://xueqiu.com/stock/forchartk/stocklist.json?");
		uri.append("symbol=").append(symbol).append("&period=1day&type=before&").append("begin=").append(begin).append("&end=").append(end);
		//type=normal
		Map<String,String> para = new HashMap<String,String>();
	try {
	    str = StockUtil.requestGet(uri.toString(), para,token);
		//System.out.println("str==" + str);
	} catch (Exception e) {
		e.printStackTrace();
	}
		return str;
	}
	
	/**
	 * 解析stock对象
	 * @param map
	 * @return
	 */
	public StockInfo parseStockInfo(Map map){
		StockInfo stockInfo = new StockInfo();
		List<StockObj> list = stockInfo.getChartlist();
		if(map != null){
			stockInfo.setSymbol(((Map)map.get("stock")).get("symbol").toString());
			ArrayList chartlist = (ArrayList)map.get("chartlist");
			StockObj obj = null;
			for(int i = 0;i<chartlist.size();i++){
				obj = new StockObj();
			    Map chartMap = (Map)chartlist.get(i);
				double open = Double.parseDouble(chartMap.get("open").toString());
				double close = Double.parseDouble(chartMap.get("close").toString());
				double high = Double.parseDouble(chartMap.get("high").toString());
				double low = Double.parseDouble(chartMap.get("low").toString());
				double ma30 = Double.parseDouble(chartMap.get("ma30").toString());
				String timeStr = chartMap.get("time").toString();
				String time = StockUtil.getFullCNPatternNow(timeStr);
				obj.setTime(time.substring(0,10));
				obj.setClosePrice(close);
				obj.setHighPrice(high);
				obj.setLowPrice(low);
				obj.setOpenPrice(open);
				obj.setMa30Price(ma30);
				list.add(obj);
			}
		}
		return stockInfo;
	}
	
	/**
	 * 获取某票规定时间段内的信息
	 * @param token
	 * @param symbol
	 * @param begin
	 * @param end
	 * @return
	 */
	public StockInfo  getStockInfoForSymbole(String token,String symbol,String begin,String end){
		String info = this.getInfo(token, symbol, begin, end);
		StockInfo stock = null;
		try{
			Map map = JsonUtils.fromJson(info, Map.class);
		    stock = this.parseStockInfo(map);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return stock;
	}
	
	/**
	 * 解析某票的历史数据，找出最大偏移量的某天数据
	 * @param info
	 * @return
	 * @throws Exception 
	 */
	public StockMaObj  parseMaInfo(StockInfo info) throws Exception{
		StockMaObj maObj = null ;
		if(info != null){
			new StockMaObj();
		
		
		double max = 0.0d;
		String symbol = info.getSymbol();
		String name = Constants.getInstance().getStockMap().get(symbol.toLowerCase());
		List<StockObj> list = info.getChartlist();
		for(StockObj stockObj : list){
			double closePrice = stockObj.getClosePrice();
			double ma30Price = stockObj.getMa30Price();
			double temp = (ma30Price-closePrice)/closePrice;

			java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.00");  
			String priceStr = df.format(temp);
			//获取格式化对象
			  // NumberFormat nt = NumberFormat.getPercentInstance();
			   
			   //设置百分数精确度2即保留两位小数
			  // nt.setMinimumFractionDigits(2);
			   //nt.f
			   //最后格式化并输出
			   //temp = nt.format(temp);
			//System.out.println("百分数：" + nt.format(temp));
		//	String percentStr = nt.format(temp);
			//int size = percentStr.length();
			double temp_double = Double.parseDouble(priceStr);
			String time = stockObj.getTime();
			//System.out.println(closePrice + "|" + ma30Price);
			if(temp_double>=max){
				max = temp_double;
				maObj = new StockMaObj();
				maObj.setSymbole(symbol);
				maObj.setLowMax(max);
				maObj.setTime(time);
				maObj.setName(name);
			}
		   }
		}
		return maObj;
	}
}
