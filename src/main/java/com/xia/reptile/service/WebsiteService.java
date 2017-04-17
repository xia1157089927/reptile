package com.xia.reptile.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xia.reptile.until.db.SpringJdbcUntil;

@Service
public class WebsiteService {
	@Autowired
	private SpringJdbcUntil db;
	
	/**
	 * 保存网站基础信息
	 * @return
	 */
	public int saveWebsiteInfo(List<String> list){
		String sql = " insert  into  t_reptitle_website " 
				   + "     (website_name,  website_url,  record_time,  over,  state) "  
				   + " values "  
				   + "     (?,  ?,  now(),  0,  1) ";
		int res = db.update(sql, list.toArray());
		return res;
	}
	
	
	
}
