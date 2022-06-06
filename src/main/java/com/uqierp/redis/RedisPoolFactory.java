package com.uqierp.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisPoolFactory {
	
	@Value("${spring.redis.host}")
	private String host;
	
	@Value("${spring.redis.port}")
	private int port;
	
	@Value("${spring.redis.password}")
	private String password;
	
	@Value("${spring.redis.timeout}")
	private int timeout;
	
	@Value("${spring.redis.jedis.pool.max-active}")
	private int maxActive;
	
	@Value("${spring.redis.jedis.pool.max-idle}")
	private int maxIdle;
	
	@Value("${spring.redis.jedis.pool.min-idle}")
	private int minIdle;
	
	@Value("${spring.redis.jedis.pool.max-wait}")
	private long maxWaitMillis;
	
	//JedisPool的实例注入到spring容器里面
	@Bean
	public JedisPool JedisPoolFactory() {
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxTotal(maxActive);
		poolConfig.setMaxIdle(maxIdle);	
		poolConfig.setMinIdle(minIdle);
		poolConfig.setMaxWaitMillis(maxWaitMillis);
		JedisPool jedisPool = new JedisPool(poolConfig,host,port,timeout,password);
		return jedisPool;
	}
}
