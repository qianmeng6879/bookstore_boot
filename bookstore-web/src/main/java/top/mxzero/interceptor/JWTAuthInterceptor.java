package top.mxzero.interceptor;


import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.Cleanup;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import top.mxzero.jwt.annotation.JWTAuthentication;
import top.mxzero.jwt.service.ITokenService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
public class JWTAuthInterceptor implements HandlerInterceptor {
    @Autowired
    private ITokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        JWTAuthentication methodAnnotation = handlerMethod.getMethodAnnotation(JWTAuthentication.class);
        if(methodAnnotation == null){
            return true;
        }

        String authorization = request.getHeader("authorization");
        if (authorization == null || !authorization.startsWith("Bearer")) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            @Cleanup PrintWriter writer = response.getWriter();
            Map<String,Object> map = new HashMap<>();
            map.put("status", response.getStatus());
            map.put("message", "请提供身份认证信息。");
            writer.write(JSONObject.toJSONString(map));
            return false;
        }

        if (!tokenService.verifyToken(authorization.substring(7))) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            @Cleanup PrintWriter writer = response.getWriter();

            Map<String,Object> map = new HashMap<>();
            map.put("status", response.getStatus());
            map.put("message", "身份认证信息无效。");
            writer.write(JSONObject.toJSONString(map));
            return false;
        }

        Jws<Claims> claimsJws = tokenService.parseToken(authorization.substring(7));
        String subject = claimsJws.getBody().getSubject();
        Map data = JSONObject.parseObject(subject, Map.class);
        List roles = (List) data.get("roles");
        List permissions = (List) data.get("permissions");
        Long userId = Long.valueOf(data.get("userId").toString());

        log.info("userId:{}", userId);
        request.setAttribute("USERID", userId);
        request.setAttribute("roles", roles);
        request.setAttribute("permissions", permissions);

        String[] requiredRoles = methodAnnotation.roles();
        String[] requiredActions = methodAnnotation.permission();

        // 判断是否有权限限制，不需要权限允许通过所有用户通过
        if(requiredRoles.length == 0 && requiredActions.length == 0){
            return true;
        }

        // ADMIN管理员允许访问任何地址
        if(roles != null && roles.contains("ADMIN")){
            return true;
        }

        // 允许普通用户访问
        if(List.of(requiredRoles).contains("USER")){
            return true;
        }

        if(roles != null && roles.size() > 0){
            for (String role: requiredRoles){
                if(roles.contains(role)){
                    return true;
                }
            }
        }


        if(permissions != null && permissions.size() > 0){
            for (String action: requiredActions){
                if(permissions.contains(action)){
                    return true;
                }
            }
        }

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        @Cleanup PrintWriter writer = response.getWriter();

        Map<String,Object> map = new HashMap<>();
        map.put("status", response.getStatus());
        map.put("message", "您无权访问。");
        writer.write(JSONObject.toJSONString(map));

        return false;
    }
}
