package com.uqierp.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

@Service
public class RedisService {
	
	@Autowired
	private JedisPool jedisPool;
	
	/**
	 * 获取对象
	 */
	public <T> T get(KeyPrefix prefix,String key,Class<T> clazz){
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			//生成真正的key,getPrefix()=className+":"+prefix
			String realKey = prefix.getPrefix() + key;
			String sval = jedis.get(realKey);
			//将String转换为Bean
			T t = stringToBean(sval,clazz);
			return t;
		}finally {
			if(jedis != null) {	
				jedis.close();
			}
		}
	}
	
	/**
	 * 设置对象,含过期时间(单位：秒)
	 */					
	public <T> boolean set(KeyPrefix prefix,String key,T value){
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			String realKey = prefix.getPrefix() + key;
			//将Bean转换为String
			String s = beanToString(value);
			if(s == null || s.length() <= 0) {
				return false;
			}
			int seconds = prefix.expireSeconds();
			if(seconds <= 0) {
				//有效期：代表不过期
				jedis.set(realKey, s);
			}else {
				jedis.setex(realKey, seconds, s);
			}
			return true;
		}finally {
			if(jedis != null) {	
				jedis.close();
			}
		}
	}
	
	/**
	 * 减少值
	 */
	public <T> Long decr(KeyPrefix prefix,String key){
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			String realKey = prefix.getPrefix() + key;
			//返回value减1后的值
			return jedis.decr(realKey);
		}finally {
			if(jedis != null) {	
				jedis.close();
			}
		}
	}
	
	/**
	 * 增加值
	 */
	public <T> Long incr(KeyPrefix prefix,String key){
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			String realKey = prefix.getPrefix() + key;
			//返回value加1后的值
			return jedis.incr(realKey);
		}finally {
			if(jedis != null) {	
				jedis.close();
			}
		}
	}
	
	/**
	 * 删除
	 */
	public boolean delete(KeyPrefix prefix, String key) {
		 Jedis jedis = null;
		 try {
			 jedis =  jedisPool.getResource();
			//生成真正的key
			String realKey  = prefix.getPrefix() + key;
			long ret =  jedis.del(realKey);
			return ret > 0;
		 }finally {
			 if(jedis != null) {	
				 jedis.close();
			 }
		 }
	}
	
	/**
	 * 检查key是否存在
	 */
	public <T> boolean exists(KeyPrefix prefix, String key) {
		Jedis jedis = null;
		try {
			jedis =  jedisPool.getResource();
			//生成真正的key
			String realKey = prefix.getPrefix() + key;
			return jedis.exists(realKey);
		 }finally {
			 if(jedis != null) {	
				 jedis.close();
			 }
		 }
	}
	
	/**
	 * 将字符串转换为Bean对象
	 */
	@SuppressWarnings("unchecked")
	public static <T> T stringToBean(String str,Class<T> clazz) {
		if(str == null || str.length() == 0 || clazz == null) {
			return null;
		}		
		if(clazz == int.class || clazz == Integer.class) {
			return ((T) Integer.valueOf(str));
		}else if(clazz == String.class) {
			return (T) str;
		}else if(clazz == long.class || clazz == Long.class) {
			return (T) Long.valueOf(str);
		}else if(clazz == List.class) {
			return JSON.toJavaObject(JSONArray.parseArray(str), clazz);
		}else {
			return JSON.toJavaObject(JSON.parseObject(str), clazz);
		}		
	}
	
	/**
	 * 将Bean对象转换为字符串类型
	 */
	public static <T> String beanToString(T value) {
		if(value == null){
			return null;
		}
		Class<?> clazz = value.getClass();
		if(clazz == int.class || clazz == Integer.class) {
			return ""+value;
		}else if(clazz == String.class) {
			return (String)value;
		}else if(clazz == long.class || clazz == Long.class) {
			return ""+value;
		}else {
			return JSON.toJSONString(value);
		}		
	}
	
}