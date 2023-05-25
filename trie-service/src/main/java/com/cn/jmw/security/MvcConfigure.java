package com.cn.jmw.security;

/**
 * @author jmw
 * @Description WebMvcConfigurer是Spring MVC提供的一个接口，用于配置Spring MVC的行为。
 * 它提供了一些方法，可以用于配置拦截器、视图解析器、消息转换器、静态资源处理等。
 * @date 2023年03月24日 17:25
 * @Version 1.0
 */

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 对 springmvc 进行自定义配置
 * 
 * Spring Security的过滤链 FilterChainProxy 是一个Servlet过滤器，它将一个请求委托给一个或多个过滤器链。
 * vs
 * Spring MVC的过滤链 DispatcherServlet 是一个Servlet，它将一个请求委托给一个或多个处理器映射器、处理器适配器和视图解析器。
 * 
 * 场景：
 * Spring MVC：进行简单的请求拦截，例如记录请求日志或者进行权限校验
 * Spring Security：进行复杂的请求拦截，例如根据不同的请求方式进行不同的权限校验。身份认证、权限校验、CSRF防护
 */
@Configuration
public class MvcConfigure implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login.html").setViewName("login");
        registry.addViewController("/index.html").setViewName("index");
        registry.addViewController("/logout.html").setViewName("logout");
        registry.addViewController("/tire.html").setViewName("trie");
    }


}

