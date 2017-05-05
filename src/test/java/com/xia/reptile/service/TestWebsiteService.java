package com.xia.reptile.service;

import java.io.IOException;
import java.net.MalformedURLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
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
		String url = "http://www.veryhot.cc/index.php?s=/vod-read-id-25473.html";
		final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_38);
		webClient.getOptions().setCssEnabled(false);
	    webClient.getOptions().setJavaScriptEnabled(true);
	    webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
	    webClient.getOptions().setThrowExceptionOnScriptError(false);
	    webClient.getOptions().setDoNotTrackEnabled(false);
	    webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		try {
			@SuppressWarnings("unused")
			final HtmlPage startPage = webClient.getPage(url);
			websiteService.equals("");
			System.out.println();
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
		}finally {
			webClient.close();
		}

		//System.out.println("直接获取网页内容：");    
        //HttpUnitOptions.setScriptingEnabled(false);  
        // 建立一个WebConversation实例    
        //WebConversation wc = new WebConversation();    
        // 向指定的URL发出请求，获取响应    
		//WebResponse wr = wc.getResponse("http://www.ygdy8.net/html/dongman/new/20120426/37429.html");    
		// 用getText方法获取相应的全部内容    
		// 用System.out.println将获取的内容打印在控制台上    
		//System.out.println(wr.getText());
	}
	
}
