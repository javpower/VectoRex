package io.github.javpower.vectorexserver.config;


import io.github.javpower.vectorexserver.interceptor.JsonRequestBodyFilter;
import io.github.javpower.vectorexserver.interceptor.LoginInterceptor;
import io.github.javpower.vectorexserver.interceptor.RefreshTokenInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gc.x
 * @date 2022/3/15
 **/
@Slf4j
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {


    @Bean
    public LoginInterceptor loginInterceptor() {
        return new LoginInterceptor();
    }
    @Bean
    public RefreshTokenInterceptor refreshTokenInterceptor() {
        return new RefreshTokenInterceptor();
    }
    @Bean
    public FilterRegistrationBean jsonRequestBodyFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        JsonRequestBodyFilter jsonRequestBodyFilter = new JsonRequestBodyFilter();
        filterRegistrationBean.setFilter(jsonRequestBodyFilter);
        List<String> urls = new ArrayList<>();
        urls.add("/*");
        filterRegistrationBean.setUrlPatterns(urls);
        return filterRegistrationBean;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> excludePaths = new ArrayList<>();
        excludePaths.add("/vectorex/login");
        excludePaths.add("/v3/api-docs/**");
        excludePaths.add("/swagger-resources/**");
        excludePaths.add("/swagger-ui/**");
        excludePaths.add("/doc.html");
        excludePaths.add("/webjars/**");
        excludePaths.add("/favicon.ico");
        excludePaths.add("/index.html");
        // 登录验证拦截器
        registry.addInterceptor(loginInterceptor()).excludePathPatterns(excludePaths);
        // 刷新token拦截器
        registry.addInterceptor(refreshTokenInterceptor());
    }

}
