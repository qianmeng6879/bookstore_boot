package top.mxzero.advice;

import lombok.extern.log4j.Log4j2;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.NoHandlerFoundException;
import top.mxzero.common.DateUtils;
import top.mxzero.common.exception.CustomException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RestControllerAdvice
@Log4j2
public class GlobalExceptionHandlerAdvice {

    @ExceptionHandler({NoHandlerFoundException.class, HttpRequestMethodNotSupportedException.class})
    public Object noHandlerFoundException(Exception exception) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();

        if(exception instanceof NoHandlerFoundException){
            response.setStatus(404);
        }else {
            response.setStatus(405);
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Map<String, Object> map = new HashMap<>();
        map.put("status", response.getStatus());
        map.put("path", request.getRequestURI());
        map.put("time", DateUtils.format(new Date()));
        map.put("message", exception.getMessage());
        return map;
    }

    @ExceptionHandler(CustomException.class)
    public Object customException(CustomException exception) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        String requestURI = request.getRequestURI();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        Map<String, Object> map = new HashMap<>();
        map.put("status", HttpServletResponse.SC_BAD_REQUEST);
        map.put("message", exception.getMessage());
        map.put("time", DateUtils.format(new Date()));
        map.put("path", requestURI);
        return map;
    }

    @ExceptionHandler(BindException.class)
    public Object bindException(BindException exception) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        String requestURI = request.getRequestURI();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

        List<String> exceptionMessages = new ArrayList<>(exception.getFieldErrors().size());
        exception.getFieldErrors().forEach(fieldError -> {
            exceptionMessages.add(fieldError.getDefaultMessage());
        });

        Map<String, Object> map = new HashMap<>();
        map.put("status", HttpServletResponse.SC_BAD_REQUEST);
        map.put("message", exceptionMessages);
        map.put("time", DateUtils.format(new Date()));
        map.put("exception", exception.getClass().getName());
        map.put("path", requestURI);
        return map;
    }


    @ExceptionHandler({Exception.class})
    public Object exceptionHandler(Exception e) {
        log.error("{} - {}", e.getMessage(), e.getStackTrace()[0]);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        Map<String, Object> map = new HashMap<>();
        map.put("path", request.getRequestURI());
        map.put("status", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        map.put("time", DateUtils.format(new Date()));
        map.put("exception", e.getClass().getName());
        map.put("message", e.getMessage());

        return map;
    }

}
