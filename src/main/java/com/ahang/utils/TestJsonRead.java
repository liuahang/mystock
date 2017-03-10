package com.ahang.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *��ȡָ��Ŀ¼�µ�json�ļ���ת����Map��
 *�û�����ģ��Զ�̷��ر��������Խӿ��á�
 *���õ���jar��pom.xml���ã�
 *    <dependency>
            <groupId>commons-io</groupId>
            <artifactId>commons-io</artifactId>
            <version>2.2</version>
          
        </dependency>
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>2.1.1</version>
            
        </dependency> 
 *
 *
 */
public class TestJsonRead {

	public static void main(String args[])  throws Exception {
		TestJsonRead test = new TestJsonRead();
		Map<String,Object> map = test.getJsonMap();
		System.out.println("map==" + map );
	}
	
	public Map  getJsonMap() throws Exception{
		String json = "";
		Map<String,Object> map = null;
		InputStream stream = this.getClass().getClassLoader().getResourceAsStream("json/policy.json");
        StringWriter writer = new StringWriter();
        IOUtils.copy(stream, writer, String.valueOf(Charset.defaultCharset()));
        json = writer.toString();
        map = new ObjectMapper().readValue(json, HashMap.class);
        return map;
	}
	
	public String getJsonString() throws Exception {
		String json = "";
		InputStream stream = this.getClass().getClassLoader().getResourceAsStream("json/policy.json");
        StringWriter writer = new StringWriter();
        IOUtils.copy(stream, writer, String.valueOf(Charset.defaultCharset()));
        json = writer.toString();
        return json;
	}
	
	
}
