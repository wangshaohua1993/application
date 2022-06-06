package com.uqierp.access;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.uqierp.result.CodeMsg;
import com.uqierp.result.Result;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;

@Service
public class AccessInterceptor  extends HandlerInterceptorAdapter{
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		/*User user = getUser(request, response);
		if(user == null){
			if("XMLHttpRequest".equals(request.getHeader("x-requested-with"))){ 
				//ajax请求返回消息提示
				render(response, CodeMsg.SESSION_ERROR);
			}
			return false;
		}*/
		return true;
	}

	private void render(HttpServletResponse response, CodeMsg cm)throws Exception {
		response.setContentType("application/json;charset=UTF-8");
		OutputStream out = response.getOutputStream();
		String str  = JSON.toJSONString(Result.error("失败"));
		out.write(str.getBytes("UTF-8"));
		out.flush();
		out.close();
	}


	
}
