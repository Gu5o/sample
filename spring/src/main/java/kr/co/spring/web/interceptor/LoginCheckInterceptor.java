package kr.co.spring.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginCheckInterceptor extends HandlerInterceptorAdapter{
	//Controller 보내기 전에 처리 하는 인터셉터
	//반환이 false라면 컨트롤러를 요청하지 않음
	
	//Controller 호출전
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
			
		HttpSession session = request.getSession(true);
		
		if(session == null) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN); // 403에러
			return false;
		}
		// /member/memberList	
		if(session.getAttribute("LOGIN_USER") == null) {
			response.sendRedirect(request.getContextPath()+"/login/loginForm");
			return false;
		}
		
		return true;
	}
	
	
	//Controller 실행 후 처리되는 내용
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		super.afterCompletion(request, response, handler, ex);
	}
	
}
