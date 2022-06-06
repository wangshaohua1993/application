package com.uqierp.shiro;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
 
@Configuration
public class ShiroConfiguration {

	@Value("${spring.redis.host}")
	private String host;

	@Value("${spring.redis.port}")
	private int port;

	@Value("${spring.redis.password}")
	private String password;

	@Value("${spring.redis.timeout}")
	private int timeout;

	//将自己的验证方式加入容器
	@Bean
	public MybatisRealm myShiroRealm(CredentialsMatcher credentialsMatcher) {
		MybatisRealm myShiroRealm = new MybatisRealm();
		myShiroRealm.setName("mybatisRealm");
		//将自定义的令牌set到了Realm
		myShiroRealm.setCredentialsMatcher(credentialsMatcher);
		return myShiroRealm;
	}

	//Filter工厂，设置对应的过滤条件和跳转条件
	@Bean
	public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		// 必须设置SecuritManager
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		// 拦截器
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
		// 配置退出过滤器,其中的具体代码Shiro已经替我们实现了
		//filterChainDefinitionMap.put("/logout", "logout");

		//过滤链定义，从上向下顺序执行，一般将 /**放在最为下边 
		//authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问

		filterChainDefinitionMap.put("/authenticate", "anon"); //不拦截登录请求
		filterChainDefinitionMap.put("/script/**", "anon");    //不拦截js
		filterChainDefinitionMap.put("/css/**", "anon");       //不拦截css
		filterChainDefinitionMap.put("/js/**", "anon");        //不拦截js
		filterChainDefinitionMap.put("/img/**", "anon");       //不拦截图片
		filterChainDefinitionMap.put("/img2/**", "anon");      //不拦截图片
		filterChainDefinitionMap.put("/public/**", "anon");    //不拦截公共资源
		filterChainDefinitionMap.put("/big_index", "anon");    //不拦截大屏首页请求
		filterChainDefinitionMap.put("/fzjq/tbsj", "anon");    //不拦截大屏首页请求
		//filterChainDefinitionMap.put("/index", "roles[ADMIN]"); //设置特定角色访问
		filterChainDefinitionMap.put("/**", "authc");

		// 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
		shiroFilterFactoryBean.setLoginUrl("/login");
		// 登录成功后要跳转的链接
		//shiroFilterFactoryBean.setSuccessUrl("/index");
		// 未授权界面;
		shiroFilterFactoryBean.setUnauthorizedUrl("/403");

		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilterFactoryBean;

	}

	//权限管理，配置主要是Realm的管理认证
	@Bean
	public SecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		//securityManager.setRealm(myShiroRealm());
		securityManager.setRealm(myShiroRealm(hashedCredentialsMatcher()));
		securityManager.setSessionManager(sessionManager());
		//配置自定义缓存redis
		securityManager.setCacheManager(cacheManager());
		return securityManager;
	}

	//session管理
	@Bean
	public SessionManager sessionManager() {
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		//设置session超时时间为1小时(单位毫秒)
		sessionManager.setGlobalSessionTimeout(3600000);
		//sessionManager.setGlobalSessionTimeout(-1);//永不超时
		//设置redisSessionDao
		sessionManager.setSessionDAO(redisSessionDAO());
		return sessionManager;
	}

	//配置cacheManager
	public RedisCacheManager cacheManager() {
		RedisCacheManager redisCacheManager = new RedisCacheManager();
		redisCacheManager.setRedisManager(redisManager());
		return redisCacheManager;
	}

	//配置redisManager
	public RedisManager redisManager() {
		RedisManager redisManager = new RedisManager();
		redisManager.setHost(host);
		redisManager.setPort(port);
		redisManager.setTimeout(timeout);
		redisManager.setPassword(password);
		//redisManager.setExpire(3600);//配置缓存过期时间(秒)
		return redisManager;
	}

	//配置redisSessionDAO
	@Bean
	public RedisSessionDAO redisSessionDAO() {
		RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
		redisSessionDAO.setRedisManager(redisManager());
		return redisSessionDAO;
	}

	//配置html页面支持shiro权限标签控制
	@Bean
	public ShiroDialect shiroDialect() {
		return new ShiroDialect();
	}

	//密码匹配凭证管理器
	public HashedCredentialsMatcher hashedCredentialsMatcher() {
		HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
		//采用SHA-512方式加密
		hashedCredentialsMatcher.setHashAlgorithmName("SHA-512");
		//设置加密次数
		hashedCredentialsMatcher.setHashIterations(1024);
		//true加密用的hex编码，false用的base64编码
		hashedCredentialsMatcher.setStoredCredentialsHexEncoded(false);
		return hashedCredentialsMatcher;
	}
 
}
