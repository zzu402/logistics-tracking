package com.htkfood.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.htkfood.entity.UserEntity;
import com.htkfood.entity.vo.UserPostVo;
import com.htkfood.exception.CommonException;
import com.htkfood.interceptor.annotation.Privileges;
import com.htkfood.util.JsonMapper;
import com.htkfood.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SecurityInterceptor implements HandlerInterceptor {
	private Logger logger = LoggerFactory.getLogger(SecurityInterceptor.class);
	private String dispatcherPrivilege="主调度,调度员";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws CommonException, Exception {
		try {
			return doPreHandle(request, response, (HandlerMethod) handler);
		} catch (CommonException e) {
			logger.error("拦截器执行异常", e);
		} catch (Exception e) {
			logger.error("系统异常", e);
		}
		return false;
	}

	private boolean doPreHandle(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler)
			throws CommonException, IOException {
		// 开始拦截
		HandlerMethod method = handler;
		Privileges privileges = method.getMethodAnnotation(Privileges.class);
		if (privileges != null) {
			if (privileges.value()[0].equals(PrivilegeConstant.OPEN_PAGE))
				return true;
			else if (privileges.value()[0].equals(PrivilegeConstant.LOGIN_PAGE)) {
				UserEntity userEntity = (UserEntity) request.getSession().getAttribute("user");
				if (userEntity != null) {
					return true;
				}
			} else {
				UserEntity userEntity = (UserEntity) request.getSession().getAttribute("user");
				if (userEntity != null) {
					List<UserPostVo> vos = userEntity.getUserPostVos();
					return true;
//					if (vos != null && vos.size() > 0) {
//						boolean isDispatcher=false;
//						String postName=null;
//						if(StringUtil.isBlank(dispatcherPrivilege)) {
//							dispatcherPrivilege="调度员,主调度";
//						}
//						for(int i=0;i<vos.size();i++) {
//							postName=vos.get(i).getPostName();
//							if(dispatcherPrivilege.contains(postName)) {
//								isDispatcher=true;
//								break;
//							}
//						}
//						if(isDispatcher) {
//							return true;
//						}else {
//							writeError(response, 100, "您没有调度员权限！");
//							return false;
//						}
//					} else {
//						writeError(response, 100, "您没有调度员权限！");
//						return false;
//					}
				}
			}
		}
		response.sendRedirect("/login");
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

	private void writeError(HttpServletResponse response, int code, String message) throws IOException {
		writeError(response, String.format("RESULT_CODE_%d", code), message);
	}

	private void writeError(HttpServletResponse response, String code, String message) throws IOException {
		response.setContentType("application/json");
		PrintWriter writer = response.getWriter();
		Map<String, Object> map = new HashMap<>();
		map.put("code", code);
		map.put("errorMsg", message);
		writer.write(JsonMapper.nonEmptyMapper().toJson(map));
		writer.flush();
		writer.close();
	}

	// private String getParameterValue(HttpServletRequest request, String
	// parameterName, boolean findCookie) {
	// String value = request.getParameter(parameterName);
	// if (StringUtil.isBlank(value)) {
	// if(findCookie){
	// Cookie[] cookies = request.getCookies();
	// if (cookies != null) {
	// for (Cookie c : cookies) {
	// if (c.getName().equalsIgnoreCase(parameterName)) {
	// return c.getValue();
	// }
	// }
	// }
	// }
	// return request.getHeader(parameterName);
	// }
	// return value;
	// }
}
