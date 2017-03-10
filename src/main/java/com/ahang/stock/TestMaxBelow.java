package com.ahang.stock;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestMaxBelow {

	public static void main(String args[]) throws Exception{
		/*String filePath = "E:\\个人东西\\stock\\all_stock.txt";
		List<String[]> list = ReaderUtil.readLinesAndClose(filePath, "|");
		for(String[] str : list){
			
			System.out.println(str[0]+ "--"+str[1]);
		}*/
		
		List<String> list = Constants.getInstance().getSymbolList();
		Map<String,String> map = Constants.getInstance().getStockMap();
		StockService service = new StockService();
		String token = "89f125c95337d63ade1a5e6ede3882de0be2282f";//service.getToken("liuahang@163.com", "ahang123457");
		//String begin = "601123200000";//
		String begin = String.valueOf(System.currentTimeMillis()-2600*24*60*60*1000l);
		String end = String.valueOf(System.currentTimeMillis());
		List<StockMaObj> maObjList = new ArrayList<StockMaObj>();
		for(int i = 0;i<list.size();i++){
			String symbol = list.get(i);
			//System.out.println("symbol=="+symbol);
			//String symbol = "sz30057316";
			//String symbol = "sz000001";
			StockInfo stockInfo = service.getStockInfoForSymbole(token, symbol, begin, end);
			StockMaObj maObj = service.parseMaInfo(stockInfo);
			//System.out.println("" + maObj.getSymbole()+"|"+maObj.getName()+"|"+maObj.getTime()+"|"+maObj.getLowMax());
			if(maObj != null){
				maObjList.add(maObj);
				saveToFile(maObj);
			}
			
		}
		//打印到file
		//printToFile(maObjList);
		Thread.sleep(500);
	}
	
	
	private static void printToFile(List<StockMaObj> list) throws Exception{
		String filePath = "C:\\Users\\liuhang\\Desktop\\temp\\SZ399006.txt";
		for(StockMaObj maObj : list){
			String data =  maObj.getSymbole()+"|"+maObj.getName()+"|"+maObj.getTime()+"|"+maObj.getLowMax();
			StockUtil.writeLinesAndClose(filePath, data);
			System.out.println(data);
		}
	}
	
	private static void saveToFile(StockMaObj maObj) throws Exception{
		String filePath = "C:\\Users\\liuhang\\Desktop\\temp\\SZ399006.txt";
		String data =  maObj.getSymbole()+"|"+maObj.getName()+"|"+maObj.getTime()+"|"+maObj.getLowMax();
		StockUtil.writeLinesAndClose(filePath, data);
	}
}
