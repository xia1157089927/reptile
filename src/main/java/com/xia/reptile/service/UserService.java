package com.xia.reptile.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xia.reptile.until.db.SpringJdbcUntil;

@Service
public class UserService {
	
	@Autowired
	private SpringJdbcUntil springJdbcUntil;
	
	public SpringJdbcUntil getSpringJdbcUntil() {
		return springJdbcUntil;
	}

	public void setSpringJdbcUntil(SpringJdbcUntil springJdbcUntil) {
		if (springJdbcUntil != null) {
			this.springJdbcUntil = springJdbcUntil;
		}
	}

	public List<Map<String, Object>> getList(){
		return springJdbcUntil.getList("select * from users", null);
	}
	
	public void saveInfo(String email, String name){
		String sql = " insert into users(email, name) values(?, ?) ";
		List<String> list = new ArrayList<>();
		list.add(email);
		list.add(name);
		springJdbcUntil.save(sql, list.toArray());
	}
	
	public void getInfo(){
		String urlStr = "https://lsp.wuliu.taobao.com/locationservice/addr/output_address_town_array.do?l1={shengCode}&l2={shiCode}&l3={quCode}&lang=zh-S&_ksTS=1493967921570_7597&callback=jsonp7598";
		String sql_sheng = " select * from t_sheng_001 ";
		String sql_shi = " select * from t_shi_001 where  a.pcode = ? ";
		String sql_qu = " select * from t_qu_001 where  a.pcode = ? ";
		
		int shengCode = 0;
		int shiCode = 0;
		int quCode = 0;
		
		List<Map<String, Object>> shengList = springJdbcUntil.queryForList(sql_sheng, new Object[]{});
		System.out.println(shengList.size());
		for (Map<String, Object> shengMap : shengList) {
			shengCode = Integer.parseInt(shengMap.get("code").toString());
			
			List<Map<String, Object>> shiList = springJdbcUntil.queryForList(sql_shi, new Object[]{shengCode});
			for (Map<String, Object> shiMap : shiList) {
				shiCode = Integer.parseInt(shiMap.get("code").toString());
				
				List<Map<String, Object>> quList = springJdbcUntil.queryForList(sql_qu, new Object[]{shiCode});
				for (Map<String, Object> quMap : quList) {
					quCode = Integer.parseInt(quMap.get("code").toString());
					
					urlStr.replaceAll("{shengCode}", shengCode+"").replaceAll("{shiCode}", shiCode+"").replaceAll("{quCode}", quCode+"");
					System.out.println(urlStr);
				}
			}
			
		}
	}
	
	
	
}
