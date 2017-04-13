package com.xia.reptile.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.xia.reptile.Application;

@RunWith(SpringJUnit4ClassRunner.class)  
@SpringBootTest(classes=Application.class)// 指定spring-boot的启动类 
@WebAppConfiguration // 由于是Web项目，Junit需要模拟ServletContext，因此我们需要给我们的测试类加上@WebAppConfiguration。
public class TestUserService {
	@Autowired
	private UserService userService;
	
	@Test
	public void getList(){
		//userDao.saveInfo("1157089927@qq.com", "Xiams");		
		userService.saveInfo("1157089927@qq.com", "Xiams"+Math.random());
		System.out.println(userService.getList());
	}
	
	
}
