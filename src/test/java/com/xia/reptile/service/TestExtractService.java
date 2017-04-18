package com.xia.reptile.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.xia.reptile.Application;
import com.xia.reptile.dto.LinkTypeData;
import com.xia.reptile.dto.Rule;

@RunWith(SpringJUnit4ClassRunner.class)  
@SpringBootTest(classes=Application.class)// 指定spring-boot的启动类 
@WebAppConfiguration // 由于是Web项目，Junit需要模拟ServletContext，因此我们需要给我们的测试类加上@WebAppConfiguration。
public class TestExtractService {
	@Test
	public void getDatasByClass() {  
        Rule rule = new Rule(  
                "http://www1.sxcredit.gov.cn/public/infocomquery.do?method=publicIndexQuery",  
        new String[] { "query.enterprisename","query.registationnumber" }, new String[] { "兴网","" },  
                "cont_right", Rule.CLASS, Rule.POST);  
        List<LinkTypeData> extracts = ExtractService.extract(rule);  
        printf(extracts);  
    }  
	  
	@Test
	public void getDatasByCssQuery() {  
	    Rule rule = new Rule("http://www.11315.com/search",  
	            new String[] { "name" }, new String[] { "兴网" },  
	            "div.g-mn div.con-model", Rule.SELECTION, Rule.GET);  
	    List<LinkTypeData> extracts = ExtractService.extract(rule);  
	    printf(extracts);  
	}  
	
	@Test
	public void getDatasByCssQueryUserBaidu() {  
	    Rule rule = new Rule("http://www.ygdy8.net/",  
	            			 new String[] { "word", "ct", "rn", "ie",    "rsv_bp", "prevct", "tn"  }, 
	            			 new String[] { "支付宝", "1", "20", "utf-8", "1",      "no",     "news" },  
            			 	 null, -1, Rule.GET);  
	    List<LinkTypeData> extracts = ExtractService.extract(rule);  
	    printf(extracts);  
	} 
	
	///html/dongman/new/20120426/37429.html
	@Test
	public void getDateBySunny() {
		String url = "http://www.ygdy8.net/html/dongman/new/20120426/37429.html";
		WebClient webClient = new WebClient(BrowserVersion.getDefault()); 
		Page page = null;
		try {
			page = webClient.getPage(url);
		} catch (FailingHttpStatusCodeException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		InputStream is;
		try {
			is = page.getWebResponse().getContentAsStream();
			System.out.println(is.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*Rule rule = new Rule("http://www.ygdy8.net/html/dongman/new/20120426/37429.html", null, null, null, -1, Rule.GET);  
		 List<LinkTypeData> extracts = ExtractService.extract(rule);  
		 printf(extracts); */ 
	}
	
	public static void main(String[] args) {
		String url = "http://www.ygdy8.net/html/dongman/new/20120426/37429.html";
		WebClient webClient = new WebClient(BrowserVersion.getDefault()); 
		Page page = null;
		try {
			page = webClient.getPage(url);
		} catch (FailingHttpStatusCodeException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		InputStream is;
		try {
			is = page.getWebResponse().getContentAsStream();
			System.out.println(is.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void printf(List<LinkTypeData> datas) {  
	    for (LinkTypeData data : datas) {  
	        if(!data.getLinkHref().contains("/game/")){
	        	System.out.println(data.getLinkText());  
	        	System.out.println(data.getLinkHref());  
	        	System.out.println("***********************************");  
	        }
	    }  
	
	}  
}
