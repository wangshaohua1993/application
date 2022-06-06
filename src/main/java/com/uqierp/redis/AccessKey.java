package com.uqierp.redis;

public class AccessKey extends BasePrefix{

	private AccessKey( int expireSeconds, String prefix) {
		super(expireSeconds, prefix);
	}
	
	public static AccessKey withExpire(int expireSeconds) {
		return new AccessKey(expireSeconds, "access");
	}
	
	public static AccessKey access = new AccessKey(1,"access");//用户访问次数有效期1秒钟
	
}
