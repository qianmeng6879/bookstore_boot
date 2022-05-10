package top.mxzero.filter;

import lombok.extern.log4j.Log4j2;
import org.slf4j.MDC;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@WebFilter
@Log4j2
public class PathFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String uuid = UUID.randomUUID().toString();
        MDC.put("requestId", uuid);
        String requestURI = request.getRequestURI();
        String ipAddress  = request.getHeader("x-forwarded-for");
        String method = request.getMethod();
        log.info("{} {} {}", ipAddress, method, requestURI);
        chain.doFilter(request,response);
    }
}
