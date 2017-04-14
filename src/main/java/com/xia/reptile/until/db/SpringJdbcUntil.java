package com.xia.reptile.until.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 执行原生	SQL 的工具类
 * @author xiams
 *
 */
@Component
public class SpringJdbcUntil {
	private static Logger logger = LoggerFactory.getLogger(SpringJdbcUntil.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Resource(name="reptileDataSource")
	private DataSource dataSource; 
	
	@Resource(name="transactionTemplateReptile")
	private TransactionTemplate transactionTemplateReptile;
	
	private static int DEFAULT_FETCHSIZE = 31;
	
	public JdbcTemplate getJdbcTemplate () {
		return jdbcTemplate;
	}
	
	/**
	 * 执行原生的SQL  select
	 * @param sql
	 * @return
	 */
	public List<Map<String,Object>> getList(String sql, Object[] objs){
		return this.getJdbcTemplate().queryForList(sql, objs);
	}
	
	/**
	 * 保存数据信息
	 * @param sql
	 * @param objs
	 * @return
	 */
	public int save(String sql, Object[] objs){
		return this.getJdbcTemplate().update(sql, objs);
	}
	
	/**
	 * 查询条件确定时
	 * @param sql 传入的sql语句: select *from table where name=? and msisdn like '%?%'
	 * @param objects 传入的参数 new Object[] { "张三",1234}
	 * @param rowMapper
	 * @return
	 */
	public Object queryForObject (String sql, Object[] objects, RowMapper<?> rowMapper) {
		Object object = null;
		object = this.getJdbcTemplate().queryForObject(sql, objects, rowMapper);
		return object;
	}
	
	/**
	 * 返回一条记录
	 * @param sql 传入的sql语句: select *from table where user_id=?
	 * @param objects
	 * @return
	 */
	public Map<String, Object> queryForMap (String sql, Object[] objects) {
		Map<String, Object> map = null;
		try {
			map = this.getJdbcTemplate().queryForMap(sql, objects);
		}
		catch (EmptyResultDataAccessException e) {
			
		}
		catch (Exception e) {
		
		}
		if (map == null) map = new HashMap<String, Object>();
		return map;
	}
	
	public Map<String, Object> queryForMap (String sql) {
		Map<String, Object> map = null;
		try {
			map = this.getJdbcTemplate().queryForMap(sql);
		}
		catch (EmptyResultDataAccessException e) {
			
		}
		catch (Exception e) {
			
		}
		
		if (map == null) map = new HashMap<String, Object>();
		return map;
	}
	
	/**
	 * 获取某个字段的值
	 * @param sql
	 * @param args
	 * @return
	 */
	public String queryForString (String sql, Object[] args) {
		try {
			return this.getJdbcTemplate().queryForObject(sql, args, String.class);
		}
		catch (Exception ex) {
			return "";
		}
	}
	
	public String queryForString (String sql) {
		try {
			return this.getJdbcTemplate().queryForObject(sql, null, String.class);
		}
		catch (Exception ex) {
			logger.error(sql);
			return "";
		}
	}
	
	public String queryForString (String sql, List<String> list) {
		try {
			return this.getJdbcTemplate().queryForObject(sql, list.toArray(), String.class);
		}
		catch (Exception ex) {
			logger.error(sql);
			return "";
		}
	}
	
	/**
	 * 返回一条记录剔除null值
	 * @param sql 传入的sql语句: select *from table where user_id=?
	 * @param objects
	 * @return
	 */
	public Map<String, Object> queryForMapNotNull (String sql, Object[] objects) {
		Map<String, Object> map = null;
		Map<String, Object> temp = new HashMap<String, Object>();
		try {
			map = this.getJdbcTemplate().queryForMap(sql, objects);
			if (map != null) {
				Set<String> s = map.keySet();
				for (Iterator<String> iter = s.iterator(); iter.hasNext();) {
					String key = notEmpty(iter.next()).toString();
					String value = notEmpty(map.get(key)).toString();
					temp.put(key, value);
				}
				
				map.clear();
			}
		}
		catch (Exception e) {
			
		}
		return temp;
	}
	
	/**
	 * 返回数据集
	 * @param sql 传入的sql语句: select *from table where user_id=?
	 * @param objects
	 * @return
	 */
	public List<Map<String, Object>> queryForList (String sql, Object[] objects) {
		return this.queryForList(sql, objects, DEFAULT_FETCHSIZE);
	}
	
	/**
	 * 返回数据集 查询时条件不确定，将条件放入一个List<String>中
	 * @param sql
	 * @param list
	 * @return
	 */
	public List<Map<String, Object>> queryForList (String sql, List<String> list) {
		return this.queryForList(sql, list.toArray(), DEFAULT_FETCHSIZE);
	}
	
	/**
	 * 查询条件不确定时返回数据集
	 * @param sql sql_where拼接 sql="select * from table where name='"+v_name+"'";
	 * @return
	 */
	public List<Map<String, Object>> queryForList (String sql) {
		return this.queryForList(sql, DEFAULT_FETCHSIZE);
	}
	
	/**
	 * 查询条件不确定时返回数据集
	 * @param sql sql_where拼接 sql="select * from table where name='"+v_name+"'";
	 * @param fetchSize 一次获取的数据条数
	 * @return
	 */
	public List<Map<String, Object>> queryForList (String sql, int fetchSize) {
		JdbcTemplate jdbc = this.getJdbcTemplate();
		jdbc.setFetchSize(fetchSize);
		
		List<Map<String, Object>> list = null;
		try {
			list = jdbc.queryForList(sql);
		}
		catch (Exception e) {
			logger.error(sql);
		}
		if (list == null) list = new ArrayList<Map<String, Object>>();
		return list;
	}
	
	/**
	 * 返回数据集
	 * @param sql 传入的sql语句: select *from table where user_id=?
	 * @param objects
	 * @param fetchSize
	 * @return
	 */
	public List<Map<String, Object>> queryForList (String sql, Object[] objects, int fetchSize) {
		JdbcTemplate jdbc = this.getJdbcTemplate();
		jdbc.setFetchSize(fetchSize);
		
		List<Map<String, Object>> list = null;
		try {
			list = jdbc.queryForList(sql, objects);
		}
		catch (Exception e) {
			logger.error(sql);
		}
		if (list == null) list = new ArrayList<Map<String, Object>>();
		return list;
	}
	
	/**
	 * 返回数据集 查询时条件不确定，将条件放入一个List<String>中
	 * @param sql
	 * @param list
	 * @param fetchSize
	 * @return
	 */
	public List<Map<String, Object>> queryForList (String sql, List<String> list, int fetchSize) {
		return this.queryForList(sql, list.toArray(), fetchSize);
	}
	
	/**
	 * insert,update,delete 操作
	 * @param sql 传入的语句 sql="insert into tables values(?,?)";
	 * @param objects
	 * @return 0:失败 1:成功
	 */
	public int update (String sql, Object[] objects) {
		int exc = 1;
		try {
			this.getJdbcTemplate().update(sql, objects);
		}
		catch (Exception e) {
			exc = 0;
		}
		return exc;
	}
	
	public int update (String sql) {
		int exc = 1;
		try {
			this.getJdbcTemplate().update(sql);
		}
		catch (Exception e) {
			exc = 0;
			logger.error(sql);
		}
		return exc;
	}
	
	/**
	 * 返还记录数
	 * @param sql 传入的sql语句 select count(*) from table where name=?
	 * @param objects 参数值
	 * @return -1:数据库异常
	 */
	public int queryForInt (String sql, Object[] objects) {
		int exc = -1;
		try {
			exc = this.getJdbcTemplate().queryForObject(sql, objects, Integer.class);
		} catch (Exception e) {
			exc = -1;
		}
		return exc;
	}
	
	/**
	 * 返还记录数
	 * @param sql 传入的sql语句 select count(*) from table where name=?
	 * @param list 参数值
	 * @return -1:数据库异常
	 */
	public int queryForInt (String sql, List<String> list) {
		return this.queryForInt(sql, list.toArray());
	}
	
	/**
	 * 返还记录数--返回记录数超出int范围
	 * @param sql 传入的sql语句直接拼接好 sql="select count(*) from table where name='"+mike+"'"
	 * @return
	 */
	public Long queryForLong (String sql) {
		return this.getJdbcTemplate().queryForObject(sql, Long.class);
	}
	
	
	/**
	 * 去除简单SQL语句统计条数 中的多余 order by
	 * @param sql
	 * @return
	 */
	public String getEasyCntSql (String sql) {
		
		String find = "(O|o)(R|r)(D|d)(E|e)(R|r)\\s+(B|b)(Y|y)";
		String tmp = "XXXXXX";
		String sql1 = sql.replaceAll(find, tmp);
		if (sql1.contains(tmp)) {
			String sql2 = sql1.substring(sql1.lastIndexOf(tmp));
			if (sql2.contains(")")) {
				String sql3 = sql2.substring(0, sql2.lastIndexOf(")"));
				sql = sql1.replaceAll(sql3, "");
				sql = sql.replaceAll(tmp, "order by");
			} else {
				sql = sql1.replaceAll(sql2, "");
				sql = sql.replaceAll(tmp, "order by");
			}
		}
		return sql;
	}
	
	/**
	 * 过程调用
	 * @param sql
	 * @param inParam
	 * @param out
	 * @return
	 */
	public ProcHelper getProcHelper (String sql) {
		ProcHelper proc = null;
		try {
			proc = new ProcHelper(this.dataSource, sql);
			proc.setSql(sql);
		}
		catch (Exception e)
		{
			logger.error(e.toString());
		}
		return proc;
	}
	
	
	/**
	 * 替换掉sql语句中的order 只替换掉最外面的order by
	 * @param sql
	 * @return
	 */
	public String replaceSqlOrder (String sql) {
		String oldSql = sql;
		sql = sql.toLowerCase();
		String newSql = "";
		if (sql.contains(" order ")) {
			String orderSql = oldSql.substring(sql.lastIndexOf(" order "));
			if (orderSql.contains(")")) {
				newSql = oldSql;
			}
			else {
				newSql = oldSql.substring(0, sql.lastIndexOf(" order "));
			}
		} else {
			newSql = oldSql;
		}
		return newSql;
	}
	
	private static String notEmpty(Object value) {
		if (value == null) {
			value = "";
		}
		return String.valueOf(value);
	}
	
	/**
	 * 事务处理
	 * @param batchSqls
	 * @return
	 */
	public int doInTransaction (final Batch batch) {
		int exc = 1;
		if (batch == null) {
			return 0;
		}
		
		try {
			exc = transactionTemplateReptile.execute(new TransactionCallback<Integer>(){
				public Integer doInTransaction(final TransactionStatus status) {
					try {
						batch.setParams(getJdbcTemplate(), status);
						batch.execute();
					}
					catch (final Exception e)
					{
						batch.rollBack();
						batch.getError(e);
						e.printStackTrace();
						return 0;
					}
					return 1;
				}
			});
		}
		catch (Exception e) {
			exc = 0;
			batch.rollBack();
			batch.getError(e);
		}
		return exc;
	}
	
}
