package com.xia.reptile.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.xia.reptile.hibernate.User;

public interface UserDao extends CrudRepository<User, Long>{
	
	@Query(value="select * from users ", nativeQuery=true)
	public List<Map<String, Object>> getList();
	
	@Modifying
	@Query(value="insert into users(email, name) values(?1, ?2)", nativeQuery=true)
	public void saveInfo(String email, String name);
	
}
