package com.xia.reptile.service;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.MalformedURLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.xia.reptile.Application;

@RunWith(SpringJUnit4ClassRunner.class)  
@SpringBootTest(classes=Application.class)// 指定spring-boot的启动类 
public class TestWebsiteService {
	@Autowired
	private WebsiteService websiteService;
	
	@Test
	public void testSaveWebsiteInfo(){
		/*List<String> params = new ArrayList<>();
		params.add("电影天堂");
		params.add("http://www.dytt8.net/");
		System.out.println(websiteService.saveWebsiteInfo(params));*/
		String url = "http://www.ygdy8.net/html/dongman/new/20120426/37429.html";
		final WebClient webClient = new WebClient(BrowserVersion.BEST_SUPPORTED);
		try {
			final HtmlPage startPage = webClient.getPage(url);
			System.out.println(startPage.toString());
			//assertEquals("HtmlUnit - Welcome to HtmlUnit", startPage.getTitleText());
		} catch (FailingHttpStatusCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//assertEquals("HtmlUnit - Welcome to HtmlUnit", startPage.getTitleText());
		/*WebClient webClient = new WebClient(BrowserVersion.getDefault()); 
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
		}*/
	}
	
}
