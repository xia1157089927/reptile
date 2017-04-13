package com.xia.reptile.service;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DemoHtml {
	private static Logger logger = LoggerFactory.getLogger(DemoHtml.class);

	public void getHtml(){
		Document doc = null;
		try {
			doc = Jsoup.connect("http://www.oschina.net/") 
					   .data("query", "Java")   // 请求参数
					   .userAgent("I ’ m jsoup") // 设置 User-Agent 
					   .cookie("auth", "token") // 设置 cookie 
					   .timeout(3000)           // 设置连接超时时间
					   .post();  // 使用 POST 方法访问 URL 
		} catch (IOException e) {
			logger.info("连接异常");
		}    
		 
		if(doc != null){
			
		} 
		 
		 
	}
	
}
