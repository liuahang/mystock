package com.ahang.stock;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.util.EntityUtils;

public class StockUtil {
	private static final String UTF_8 = "UTF-8";

	private static final int TIMEOUT = 6000;
	public static final int CONNECTION_TIMEOUT = 100000;
	public static final String CONTENT_TYPE_APPLICATION_JSON = "application/json";
    /** 中文格式的年月日时分秒格式 <code>yyyy-MM-dd HH:mm:ss</code> */
    public static final String FULL_CHINESE_PATTERN = "yyyy-MM-dd HH:mm:ss";
	private StockUtil() {
	}
	
	
	public static String requestGet(String url, Map<String, String> headerMap,String token)  {  
		String jsonStr = "";
		//String token="10c899bff96d3526c7040bebe21b524a21bc72fd";
		BasicCookieStore cookieStore = new BasicCookieStore();
		BasicClientCookie cookie = new BasicClientCookie("xq_a_token",
				token);
		cookie.setDomain(".xueqiu.com");
		//cookie.setPath("/");
		//cookie.setAttribute("xq_r_token", "b9909cad217725f6061dd2ce318f6f19a1f3e83b");
		cookieStore.addCookie(cookie);
        HttpGet httpget = new HttpGet(url);  
        //配置请求的超时设置  
        RequestConfig requestConfig = RequestConfig.custom()    
                .setConnectionRequestTimeout(CONNECTION_TIMEOUT)  
                .setConnectTimeout(CONNECTION_TIMEOUT)    
                .setSocketTimeout(CONNECTION_TIMEOUT).build();    
        httpget.setConfig(requestConfig);
        httpget.addHeader("Content-Type", CONTENT_TYPE_APPLICATION_JSON);
        //httpPost.addHeader("Authorization", headerMap.get("Authorization"));
        for(Map.Entry<String, String> entry:headerMap.entrySet()){    
        	httpget.addHeader(entry.getKey(), entry.getValue());
        }
        
        CloseableHttpClient httpclient = HttpClientBuilder.create().setDefaultCookieStore(cookieStore).build();  
        CloseableHttpResponse response;
		try {
			response = httpclient.execute(httpget);
			 if(response != null ){
	        	 HttpEntity entity = response.getEntity();          
	             jsonStr = EntityUtils.toString(entity);
	             httpget.releaseConnection();  
	        }
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
         
        return jsonStr;
	}
	
	
	/**
	 * @功能: 发送Apache post请求
	 * @作者: yangc
	 * @创建日期: 2013-1-9 上午11:31:24
	 */
	public static String getXieQiuToken(String uri, Map<String, Object> paramsMap) {
		String token = "";
		CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(uri);
		httpPost.setConfig(RequestConfig.custom().setConnectionRequestTimeout(TIMEOUT).setConnectTimeout(TIMEOUT).build());

		MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create().setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
		if (paramsMap != null && !paramsMap.isEmpty()) {
			for (Entry<String, Object> entry : paramsMap.entrySet()) {
				if (entry.getValue() instanceof File) {
					multipartEntityBuilder.addBinaryBody(entry.getKey(), (File) entry.getValue());
				} else {
					multipartEntityBuilder.addTextBody(entry.getKey(), entry.getValue().toString(), ContentType.create("text/plain", UTF_8));
				}
			}
		}
		httpPost.setEntity(multipartEntityBuilder.build());
		BufferedReader br = null;
		try {
			HttpResponse httpResponse = closeableHttpClient.execute(httpPost);
			Header[] headers = httpResponse.getAllHeaders();
			boolean isBreak = false;
			if(headers !=null && headers.length>0){
				//Set-Cookie|xq_a_token
				for(int i = 0;i<headers.length;i++){
					String name = headers[i].getName();
					if(name.equals("Set-Cookie")){
						String value = headers[i].getValue();
						String[] values = value.split(";");
						for(String str : values){
							if(str.contains("xq_a_token")){
								token = str.split("=")[1];
								isBreak = true;
								break;
							}
						}
						if(isBreak){
							break;
						}
						
					}
				}
			  }
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
					br = null;
				}
				closeableHttpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return token;
	}

	/**
	 * 
	 * @param str格式如：Mon Dec 14 00:00:00 +0800 2015
	 * @return
	 */
	public static String getFullCNPatternNow(String str){
		Date date = new Date(str);
		return getFullCNPatternNow(date);
	}
	
	/**
     * 返回中文格式的当前日期
     * 
     * @param date 日期 @see Date
     * @return [yyyy-MM-dd HH:mm:ss]
     */
    public static String getFullCNPatternNow(Date date) {
        return formatDate(date, FULL_CHINESE_PATTERN);
    }
    /**
     * 将日期转换为指定的字符串格式
     * 
     * @param date 日期 @see Date
     * @param format 日期格式
     * @return 格式化后的日期字符串，如果<code>date</code>为<code>null</code>或者 <code>format</code>为空，则返回<code>null</code>。
     */
    public static String formatDate(final Date date, String format) {

        return new SimpleDateFormat(format).format(date);
    }
    
    
    /**
     * 将字符串转换撑日期对象
     * 
     * @param sDate 日期字符串
     * @param format 日期格式 @see DateFormat
     * @return 日期对象 @see Date
     */
    public static Date parseDate(String sDate, String format) {
        return parseDate(sDate, format, null);
    }
    
    /**
     * 将字符串转换成日期对象
     * 
     * @param sDate 日期字符串
     * @param format 日期格式 @see DateFormat
     * @param defaultValue 默认值
     * @return 日期对象，如果格式化失败则返回默认值<code>defaultValue</code>
     */
    public static Date parseDate(String sDate, String format, Date defaultValue) {

        DateFormat formatter = new SimpleDateFormat(format);
        try {
            return formatter.parse(sDate);
        } catch (ParseException e) {
            return defaultValue;
        }

    }

    
    /**
     * 写入数据并关闭流
     * 
     * @param filePath 文件路径
     * @param data 数据
     * @throws IOException
     */
    public static boolean writeLinesAndClose(String filePath, Object data) throws IOException {
        return writeLinesAndClose(filePath, data, true);
    }

    /**
     * 写入文件并关闭
     * 
     * @param filePath 文件路径
     * @param data 数据
     * @param append 是否追加
     * @return 是否成功，如果<code>path</code>为空或者<code>path</code>为<code>null</code> ,则返回<code>false</code>
     * @throws IOException
     */
    public static boolean writeLinesAndClose(String filePath, Object data, boolean append) throws IOException {
        return writeLinesAndClose(filePath, data, append, "UTF-8");
    }
    
    /**
     * 写入文件并关闭
     * 
     * @param filePath 文件路径
     * @param data 数据
     * @param append 是否追加
     * @param charsetName 编码格式
     * @return 是否成功，如果<code>path</code>为空或者<code>path</code>为<code>null</code> ,则返回<code>false</code>
     * @throws IOException
     */
    public static boolean writeLinesAndClose(String filePath, Object data, boolean append, String charsetName)
            throws IOException {
        if (filePath == null || data == null) {
            return false;
        }
       
        Writer writer = null;
        try {
            writer = getWriter(filePath, append, charsetName);
            writer.append(data.toString()).append("\n");
            return true;
        } finally {
        	 if (writer != null) {
                 try {
                	 writer.close();
                 } catch (IOException ignore) {
                     // can ignore
                 }
             }
        }
    }

    private static Writer getWriter(String filePath, boolean append, String charsetName) throws IOException {
        OutputStream output = new FileOutputStream(filePath, append);

        Writer writer = new OutputStreamWriter(output, charsetName);
        return new BufferedWriter(writer);
    }

    private static Writer getWriter(File file, boolean append, String charsetName) throws IOException {
        OutputStream output = new FileOutputStream(file, append);

        Writer writer = new OutputStreamWriter(output, charsetName);
        return new BufferedWriter(writer);
    }
    
    

    
    /**
     * 获取输入流
     * 
     * @param filePath 文件路径
     * @return <code>InputStream</code> 如果找不到。
     * @throws FileNotFoundException
     */
    private static InputStream getInputStream(String filePath) throws FileNotFoundException {
        return new FileInputStream(filePath);
    }
    
    public static void main(String args[]){
    	Date date = new Date("Mon Dec 14 00:00:00 +0800 2015");
    	long seconds = date.getTime();
    	System.out.println(seconds);
    	Date my = parseDate("2010-06-01 00:00:00",FULL_CHINESE_PATTERN);
    	System.out.println(my.getTime());
    	String str = getFullCNPatternNow(date);
    	System.out.println("str==" + str);
    }
}
