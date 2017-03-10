package com.ahang.stock;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import com.baidu.unbiz.common.io.ReaderUtil;

public class Constants {
    private static Constants instance = null;
    private static Map<String,String> stockMap = new HashMap<String,String>();
    private static  List<String>  symbolList = new ArrayList<String>();
    private static List<String>  stockNameList = new ArrayList<String>();
	private Constants(){
		String filePath = "E:\\个人东西\\stock\\all_stock.txt";
		List<String[]> list = null;//ReaderUtil.readLinesAndClose(filePath, "|");
		for(String[] str : list){
			stockMap.put(str[0], str[1]);
			symbolList.add(str[0]);
			stockNameList.add(str[1]);
		}
		
	}
	
	public  static Constants getInstance(){
		if(instance == null){
			instance = new Constants();
		}
		
		return instance;
	}
	
	public static Map<String,String> getStockMap(){
		return stockMap;
	}

	public static List<String> getSymbolList() {
		return symbolList;
	}

	public static List<String> getStockNameList() {
		return stockNameList;
	}
	
	
}
