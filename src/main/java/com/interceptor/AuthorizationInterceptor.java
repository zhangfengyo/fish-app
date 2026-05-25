package com.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.annotation.IgnoreAuth;
import com.entity.TokenEntity;
import com.service.TokenService;
import com.utils.R;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Token authorization interceptor.
 */
@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

    public static final String LOGIN_TOKEN_KEY = "Token";

    @Autowired
    private TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,request-source,Token, Origin,imgType, Content-Type, cache-control,postman-token,Cookie, Accept,authorization");

        String origin = request.getHeader("Origin");
        if (StringUtils.isNotBlank(origin)) {
            response.setHeader("Access-Control-Allow-Origin", origin);
        }

        if (RequestMethod.OPTIONS.name().equals(request.getMethod())) {
            response.setStatus(HttpStatus.OK.value());
            return false;
        }

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        IgnoreAuth annotation = handlerMethod.getMethodAnnotation(IgnoreAuth.class);

        String token = request.getHeader(LOGIN_TOKEN_KEY);
        TokenEntity tokenEntity = null;
        if (StringUtils.isNotBlank(token)) {
            tokenEntity = tokenService.getTokenEntity(token);
        }

        if (tokenEntity != null) {
            request.getSession().setAttribute("userId", tokenEntity.getUserid());
            request.getSession().setAttribute("role", tokenEntity.getRole());
            request.getSession().setAttribute("tableName", tokenEntity.getTablename());
            request.getSession().setAttribute("username", tokenEntity.getUsername());
        }

        if (annotation != null || tokenEntity != null) {
            return true;
        }

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().write(JSONObject.toJSONString(R.error(401, "未授权，请先登录")));
        response.getWriter().flush();
        return false;
    }
}
