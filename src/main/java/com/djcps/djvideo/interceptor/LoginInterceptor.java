package com.djcps.djvideo.interceptor;

import com.djcps.djvideo.common.RetResponse;
import com.djcps.djvideo.utils.JwtUtils;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author 有缘
 */
public class LoginInterceptor implements HandlerInterceptor {
    private static Gson gson = new Gson();

    /**
     * 进入controller之前调用
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if (token == null) {
            token = request.getParameter("token");
        }
        if (token != null) {
            Claims claims = JwtUtils.checkJWT(token);
            if (claims != null) {
                Integer uid = (Integer) claims.get("id");
                String name = (String) claims.get("name");
                request.setAttribute("user_id", uid);
                request.setAttribute("name", name);
                return true;
            }
        }
        setJsonMessage(response, RetResponse.makeErrRsp("请先登录！"));
        return false;
    }

    /**
     * 响应数据给前端
     *
     * @param response
     * @param object
     */
    public static void setJsonMessage(HttpServletResponse response, Object object) {
        response.setContentType("application/json,charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        try {
            PrintWriter printWriter = response.getWriter();
            printWriter.write(gson.toJson(object));
            printWriter.close();
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
