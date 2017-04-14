package com.xia.reptile.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.xia.reptile.Application;
import com.xia.reptile.until.db.BatchSql;
import com.xia.reptile.until.db.SpringJdbcUntil;

@RunWith(SpringJUnit4ClassRunner.class)  
@SpringBootTest(classes=Application.class)// 指定spring-boot的启动类 
@WebAppConfiguration // 由于是Web项目，Junit需要模拟ServletContext，因此我们需要给我们的测试类加上@WebAppConfiguration。
public class TestUserService {
	@Autowired
	private UserService userService;
	
	@Autowired
	private SpringJdbcUntil db;  
	
	@Test
	public void getList(){
		//userDao.saveInfo("1157089927@qq.com", "Xiams");		
		userService.saveInfo("1157089927@qq.com", "Xiams"+Math.random());
		System.out.println(userService.getList());
	}
	
	@Test
	public void batchInsert(){
		BatchSql batchSql = new BatchSql();
		String sql = "insert into users(email, name) values(?, ?)";
		
		for (int i = 0; i < 10; i++) {
			batchSql.addBatch(sql, new Object[]{i+"_1157089927@qq.com", "Xiams" + i});
		}
		db.doInTransaction(batchSql);
		
	}
	
	
}
