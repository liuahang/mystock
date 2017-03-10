package com.ahang.stock;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
//import org.apache.logging.log4j.Level;
import com.ahang.utils.JsonUtils;
/**
 * 测试上证50etf和创业板50etf的二八轮动
 * @author root
 *
 */
public class Test28Plus {


	public static void main(String args[]) throws Exception {
		String token = "";
		BufferedReader br = null;
		functionList();
        try {

            br = new BufferedReader(new InputStreamReader(System.in));

            while (true) {

                System.out.println("请选择接口 : ");
                String input = br.readLine();

                if ("1".equals(input)) {
                	token = getLoginToken();
                    System.out.println("token==" + token);
                   // System.out.println("------------------------------\n");
                }else if("2".equals(input)){
                	 //String token = "89f125c95337d63ade1a5e6ede3882de0be2282f";
                     testSH50_SZ50_28Plus(token);	
                    // System.out.println("------------------------------\n");
                }else if("q".equals(input)){
                	System.exit(0);
                }else if("3".equals(input)){
                	StockService service = new StockService();
                	System.out.println("请输入代码:");
                	BufferedReader br_stock = null;
                	br_stock = new BufferedReader(new InputStreamReader(System.in));
                	 String input_stock = br_stock.readLine();
                	 System.out.println("代码==" + input_stock);
                	 double diff_price = getDiffer(service,token,input_stock);
                	 System.out.println("diff_price==" + diff_price);
                }
                else if("help".equals("help")){
                	functionList();
                }
                System.out.println("------------------------------\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
       	
		
	}
	
	
	public static void functionList(){
		 System.out.println("(1)、登录");
		 System.out.println("(2)、上证50对比创业板50");
		 System.out.println("(3)、单个股票查询");
		 System.out.println("(q)、退出");
		 System.out.println("help");
	}
	
	/**
	 * 根据用户名和密码获取token
	 * @return
	 */
	public static String getLoginToken(){
		String userName = "liuahang@163.com";
		String password = "ahang123457";
		StockService service = new StockService();
    	return service.getToken(userName, password); 	
	}
	
	
	/**
	 * 比较上证50和创业板50的涨幅
	 */
	public static void testSH50_SZ50_28Plus(String token){
		String sh50 = "SH000016";
		String sz50 = "SZ399673";
		StockService service = new StockService();
		Double sh000016 = getDiffer(service,token,sh50);
		Double sz399006 = getDiffer(service,token,sz50);//SZ399006
		String sh000016_str = DobuleToStr(sh000016);
		String sz399006_str = DobuleToStr(sz399006);
		if(sh000016<0&&sz399006<0){
			System.out.println("上证50涨幅:"+sh000016_str + ",    创业板50涨幅："+sz399006_str + ",   持有：银华日利");
		}else if(sh000016<0&&sz399006>0){
			System.out.println("上证50涨幅:"+sh000016_str + ",   创业板50涨幅："+sz399006_str + ",   持有：创业板50");
		}else if(sh000016>0&&sz399006<0){
			System.out.println("上证50涨幅:"+sh000016_str + ",   创业板50涨幅："+sz399006_str + ",   持有：上证50");
		}else if(sh000016>0&&sz399006>0){
			if(sh000016>sz399006){
				
				System.out.println("上证50涨幅:"+sh000016_str + ",   创业板50涨幅："+sz399006_str + ",   持有：上证50");
			}else{
				System.out.println("上证50涨幅:"+sh000016_str + ",   创业板50涨幅："+sz399006_str + ",   持有：创业板50");
			}
			
		}
	}
	public static String DobuleToStr(Double mydouble){
		DecimalFormat df = new DecimalFormat("0.####"); // ##表示2位小数  
		Double d = new Double(mydouble);  
		return df.format(d);
	}
	
	private static double  getDiffer(StockService service,String token,String symbol){
		String end = String.valueOf(System.currentTimeMillis());
		String begin = String.valueOf(System.currentTimeMillis() - 40*24*60*60*1000l);
		String stockInfo = service.getInfo(token,symbol,begin,end);
		Map map = JsonUtils.fromJson(stockInfo, Map.class);
		StockInfo stock = service.parseStockInfo(map);
		return average192021(stock);
		
	}
	private static double average192021(StockInfo stockInfo){
		double temp_double = 0.0d;
		List<StockObj> list = stockInfo.getChartlist();
		int size = list.size();
		int index19 = size-20;
		int index20 = size - 21;
		int index21 = size - 22;
		if(index21>0){
			double closePrice19 = list.get(index19).getClosePrice();
			double closePrice20 = list.get(index20).getClosePrice();
			double closePrice21 = list.get(index21).getClosePrice();
			double closePriceToday = list.get(size-1).getClosePrice();
			double average = (closePrice19 + closePrice20 + closePrice21)/3;
			String priceStr = formateDouble(average);
		    temp_double = Double.parseDouble(priceStr);	
		    temp_double = closePriceToday - average;
		    temp_double = Double.parseDouble(formateDouble(temp_double/average));
		}
		return temp_double;
	}
	
	
	private static String formateDouble(double doubleNumber){
		  DecimalFormat decimalFormat = new DecimalFormat("#.0000");//格式化设置  
		  String priceStr = decimalFormat.format(doubleNumber);  
		return priceStr;
	}
	private static void printTime(List<StockObj> list,String filePaht) throws Exception{
		for(StockObj obj : list){
			StockUtil.writeLinesAndClose(filePaht,obj.getTime());
		}
	}
	
	private static void printClosePrice(List<StockObj> list,String filePath)  throws Exception{
		for(StockObj obj : list){
			StockUtil.writeLinesAndClose(filePath,obj.getClosePrice());
		}
	}
	
	public static List<StockObj> handle30(List<StockObj> list){
		List<StockObj> returnList = new ArrayList<StockObj>();
		StockObj temp = null;
		if(list != null && list.size()>0){
			int size = list.size();
			
			for(int i = 0;i<size;i++){
				int befor29 = i-29;
				int befor30 = i-30;
				int befor31 = i-31;
				StockObj obj = list.get(i);
				if(befor31<0){
					temp = new StockObj();
					temp.setTime(obj.getTime());
					temp.setClosePrice(0d);
					returnList.add(temp);
				}else{
					StockObj obj19 = list.get(befor29);
					StockObj obj20 = list.get(befor30);
					StockObj obj21 = list.get(befor31);
					temp = new StockObj();
					temp.setClosePrice((obj19.getClosePrice()+obj20.getClosePrice()+obj21.getClosePrice())/3);
					temp.setTime(obj.getTime());
					returnList.add(temp);
				}
			}
			
		}
		return returnList;
	}
	
	
	public static List<StockObj> handle20(List<StockObj> list){
		List<StockObj> returnList = new ArrayList<StockObj>();
		StockObj temp = null;
		if(list != null && list.size()>0){
			int size = list.size();
			
			for(int i = 0;i<size;i++){
				int befor19 = i-19;
				int befor20 = i-20;
				int befor21 = i-21;
				StockObj obj = list.get(i);
				if(befor21<0){
					temp = new StockObj();
					temp.setTime(obj.getTime());
					temp.setClosePrice(0d);
					returnList.add(temp);
				}else{
					StockObj obj19 = list.get(befor19);
					StockObj obj20 = list.get(befor20);
					StockObj obj21 = list.get(befor21);
					temp = new StockObj();
					temp.setClosePrice((obj19.getClosePrice()+obj20.getClosePrice()+obj21.getClosePrice())/3);
					temp.setTime(obj.getTime());
					returnList.add(temp);
				}
			}
			
		}
		return returnList;
	}
	

}
