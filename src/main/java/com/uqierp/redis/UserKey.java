package com.uqierp.redis;

public class UserKey extends BasePrefix{

	public static final int TOKEN_EXPIRE = 3600*24*2;
	private UserKey(int expireSeconds, String prefix) {
		super(expireSeconds, prefix);
	}
	public static UserKey token = new UserKey(TOKEN_EXPIRE, "tk");//用户token有效期2天
	public static UserKey getById = new UserKey(0, "id");//用户id永不过期
	public static UserKey getTokenById = new UserKey(0, "tokenid");//tokenid永不过期
}
