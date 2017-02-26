package com.xkxx.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.xkxx.entity.Account;


public class LoginInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2, ModelAndView arg3) throws Exception {
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object obj) throws Exception {
		/*Account loginAcc = (Account) request.getSession().getAttribute("loginAcc");
		if(loginAcc == null) {
			String str = "<script language='javascript'>alert('页面已超时,请重新登录!');" +
					"window.top.location.href='../login/toLogin.do';</script>";
			response.setContentType("text/html;charset=UTF-8");
			try{
				PrintWriter writer = response.getWriter();
				writer.write(str);
				writer.flush();
				response.sendRedirect(
						request.getContextPath() + "/login/toLogin.do");
				writer.close();
			}catch(Exception e){
				e.printStackTrace();
			}
			return false;
		} else {
			return true;
		}*/
		return true;
	}
}
