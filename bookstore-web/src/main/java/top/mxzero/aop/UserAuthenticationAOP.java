package top.mxzero.aop;


import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.Cleanup;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.mxzero.jwt.annotation.JWTAuthentication;
import top.mxzero.jwt.service.ITokenService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Aspect
//@Component
@Log4j2
public class UserAuthenticationAOP {
    @Autowired
    private ITokenService tokenService;

//    @Around("execution(* top.mxzero.service.*.*(..))")
    public Object before(ProceedingJoinPoint joinPoint) throws Throwable{
        System.out.println("sign " + joinPoint.getSignature());
        return joinPoint.proceed();
    }

    @Around("@annotation(top.mxzero.jwt.annotation.JWTAuthentication)")
    public Object auth(ProceedingJoinPoint point) throws Throwable {
        System.out.println("sign " + point.getSignature());
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
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
            return null;
        }

        if (!tokenService.verifyToken(authorization.substring(7))) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            @Cleanup PrintWriter writer = response.getWriter();

            Map<String,Object> map = new HashMap<>();
            map.put("status", response.getStatus());
            map.put("message", "身份认证信息无效。");
            writer.write(JSONObject.toJSONString(map));
            return null;
        }

        Jws<Claims> claimsJws = tokenService.parseToken(authorization.substring(7));
        String subject = claimsJws.getBody().getSubject();
        Map data = JSONObject.parseObject(subject, Map.class);
        List groups = (List) data.get("group");
        List permissions = (List) data.get("permission");

        log.info("userId:{}", data.get("userId"));
        request.setAttribute("USERID", data.get("userId"));
        request.setAttribute("roles", groups);
        request.setAttribute("permissions", permissions);


        Object[] args = point.getArgs();
        Class<?>[] argTypes = new Class[point.getArgs().length];
        for (int i = 0; i < args.length; i++) {
            argTypes[i] = args[i].getClass();
        }
        Method method = null;
        try {
            method = point.getTarget().getClass()
                    .getMethod(point.getSignature().getName(), argTypes);
        } catch (NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }

        assert method != null;
        JWTAuthentication jwtAuthentication = method.getAnnotation(JWTAuthentication.class);

        String[] requiredRoles = jwtAuthentication.roles();
        String[] requiredPermissions = jwtAuthentication.permission();

        // 判断是否有权限限制，不需要权限允许通过所有用户通过
        if(requiredRoles.length == 0 && requiredPermissions.length == 0){
            return point.proceed();
        }

        // ADMIN管理员允许访问任何地址
        if(groups != null && groups.contains("ADMIN")){
            return point.proceed();
        }

        if(groups != null && groups.size() > 0){
            for (String role: requiredRoles){
                if(groups.contains(role)){
                    return point.proceed();
                }
            }
        }


        if(permissions != null && permissions.size() > 0){
            for (String permission: requiredPermissions){
                if(permissions.contains(permission)){
                    return point.proceed();
                }
            }
        }
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        @Cleanup PrintWriter writer = response.getWriter();

        Map<String,Object> map = new HashMap<>();
        map.put("status", response.getStatus());
        map.put("message", "您无权访问。");
        writer.write(JSONObject.toJSONString(map));
        return null;
    }
}
