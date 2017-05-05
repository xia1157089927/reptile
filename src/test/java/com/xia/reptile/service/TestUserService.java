package com.xia.reptile.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.xia.reptile.Application;
import com.xia.reptile.until.HttpsUtils;
import com.xia.reptile.until.db.BatchSql;
import com.xia.reptile.until.db.SpringJdbcUntil;

@RunWith(SpringJUnit4ClassRunner.class)  
@SpringBootTest(classes=Application.class)// 指定spring-boot的启动类 
//@WebAppConfiguration // 由于是Web项目，Junit需要模拟ServletContext，因此我们需要给我们的测试类加上@WebAppConfiguration。
public class TestUserService {
	//https://www.taobao.com/home/js/sys/districtselector.js?t=20140318.js    JS入口
	
	private static Logger logger = LoggerFactory.getLogger(SpringJdbcUntil.class);
	@Autowired
	private UserService userService;
	
	@Autowired
	private SpringJdbcUntil db;  
	
	@Test
	public void getList(){
		//userDao.saveInfo("1157089927@qq.com", "Xiams");		
		//userService.saveInfo("1157089927@qq.com", "Xiams"+Math.random());
		System.out.println(userService.getList());
		userService.getInfo();
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
	
	@Test
	public void getRemoteData(){
		String sql_sheng = " select * from t_sheng_001 ";
		String sql_shi = " select * from t_shi_001 a where  a.pcode = ? ";
		String sql_qu = " select * from t_qu_001 a where  a.pcode = ? ";
		String insert_sql = "insert into t_detail_001(code, name, pcode, pingying) values(?, ?, ?, ?)";
		
		int shengCode = 0;
		int shiCode = 0;
		int quCode = 0;
		
		
		int count = 0;
		List<Map<String, Object>> shengList = db.queryForList(sql_sheng, new Object[]{});
		for (Map<String, Object> shengMap : shengList) {
			shengCode = Integer.parseInt(shengMap.get("code").toString());
			
			List<Map<String, Object>> shiList = db.queryForList(sql_shi, new Object[]{shengCode});
			for (Map<String, Object> shiMap : shiList) {
				shiCode = Integer.parseInt(shiMap.get("code").toString());
				
				List<Map<String, Object>> quList = db.queryForList(sql_qu, new Object[]{shiCode});
				for (Map<String, Object> quMap : quList) {
					quCode = Integer.parseInt(quMap.get("code").toString());
					String urlStr = "https://lsp.wuliu.taobao.com/locationservice/addr/output_address_town_array.do?l1=shengCode&l2=shiCode&l3=quCode&lang=zh-S&_ksTS=1493967921570_7597&callback=jsonp7598";
					urlStr = urlStr.replaceAll("shengCode", shengCode+"").replaceAll("shiCode", shiCode+"").replaceAll("quCode", quCode+"");

					try {
						String resPstr = HttpsUtils.post(urlStr, new HashMap<>(), new HashMap<>(), null);
						String endStr = resPstr.substring(resPstr.indexOf("{"), resPstr.indexOf("}")+1);
						Gson gson = new Gson();
						JSONObject json = new JSONObject(endStr);

						List<List<String>> list = gson.fromJson(json.get("result").toString(), new TypeToken<List<List<String>>>() {
							private static final long serialVersionUID = -9191093792962144105L;
						}.getType());
						count++;
						logger.info("-------{}----start------", count);
						for (List<String> list2 : list) {
							logger.info("{},{},{},{}",list2.get(0), list2.get(1), list2.get(2), list2.get(3));
							db.save(insert_sql, new Object[]{list2.get(0), list2.get(1), list2.get(2), list2.get(3)});
						}
						logger.info("-------{}----end------", count);
						logger.info("-------^^^^^^^^^------");
						logger.info("-------^^^^^^^^^------");
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
			}
			
		}
		
	}
	
	
}
