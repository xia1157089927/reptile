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
	
	
	
}
