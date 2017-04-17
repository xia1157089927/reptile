package com.xia.reptile.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xia.reptile.Application;

@RunWith(SpringJUnit4ClassRunner.class)  
@SpringBootTest(classes=Application.class)// 指定spring-boot的启动类 
public class TestWebsiteService {
	@Autowired
	private WebsiteService websiteService;
	
	@Test
	public void testSaveWebsiteInfo(){
		List<String> params = new ArrayList<>();
		params.add("电影天堂");
		params.add("http://www.dytt8.net/");
		System.out.println(websiteService.saveWebsiteInfo(params));
	}
	
}
