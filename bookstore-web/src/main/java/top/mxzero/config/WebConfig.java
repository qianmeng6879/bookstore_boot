package top.mxzero.config;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.mxzero.interceptor.MDCDefaultInterceptor;
import top.mxzero.interceptor.JWTAuthInterceptor;

import java.util.List;

@Configuration
@ServletComponentScan("top.mxzero.filter")
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(mdcDefaultInterceptor()).addPathPatterns("/**").order(-10000);
        registry.addInterceptor(jwtAuthInterceptor()).addPathPatterns("/**");

    }

    @Bean
    public JWTAuthInterceptor jwtAuthInterceptor(){
        return new JWTAuthInterceptor();
    }

    @Bean
    public MDCDefaultInterceptor mdcDefaultInterceptor(){
        return new MDCDefaultInterceptor();
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new BufferedImageHttpMessageConverter());
    }

}
