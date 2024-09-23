package org.example.springv3.core.config;

import jakarta.servlet.FilterRegistration;
import lombok.Builder;
import org.example.springv3.core.filter.JwtAuthorizationFilter;
import org.example.springv3.user.User;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;

/**
 * @Controller, @RestController, @Service, @Repository, @Component, @Configuration
 */


@Configuration // Ioc에 등록
public class FilterConfig {

    public FilterConfig() {
        System.out.println("FilterConfig");
    }

    @Bean
    public User go() {
        System.out.println("gogogogo");
        return User.builder().id(1).build();
    }

    @Bean
    public FilterRegistrationBean<JwtAuthorizationFilter> jwtAuthorizationFilter() {
        FilterRegistrationBean<JwtAuthorizationFilter> bean = new FilterRegistrationBean<>(new JwtAuthorizationFilter());
        // 우리가 만든 JwtAuthorizationFilter 필터가 등록됨
        // 발동 시점 등록하기
        bean.addUrlPatterns("/api/*"); // api라고 붙여져 있는게 들어오면 발동!
        bean.setOrder(0); // 0부터 시작
        return bean;
    }


}
