package com.uqierp.result;

/**
 * 封装响应数据
 */

public class Result<T> {

	private int code;
	private String message;
	private T data;
	
	/**
	 *  成功时候的调用
	 */
	public static  <T> Result<T> success(T data){
		return new Result<T>(data);
	}
	
	/**
	 *  失败
	 */
	public static  <T> Result<T> fail(){
		return new Result<T>();
	}
	
	/**
	 *  异常时候的调用
	 */
	public static <T> Result<T> error(String codeMsg){
		return new Result<T>(codeMsg);
	}
	
	private Result(T data) {
		this.code = 000000;
		this.message = "请求成功";
		this.data = data;
	}
	
	private Result() {
		this.code = -1;
		this.message = "失败！";
	}
	
	private Result(String codeMsg) {
		this.code = -1;
		this.message = codeMsg;
	}
	
	public int getCode() {
		return code;
	}
	
	public void setCode(int code) {
		this.code = code;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
		
}
