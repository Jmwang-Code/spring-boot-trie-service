package com.cn.jmw.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author jmw
 * @Description TODO
 * @date 2023年03月24日 9:41
 * @Version 1.0
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //springboot3.0
//    @Bean
//    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests()
////               .requestMatchers("/doc.html","/v3/api-docs/**").authenticated() //精细控制
////                .requestMatchers(HttpMethod.POST,"/Tire/**").permitAll()
//                .requestMatchers("/css/**", "/js/**", "/login", "/logout").permitAll()
//                .anyRequest().authenticated() // 所有请求都需要认证，除了上述的
//                .and()
//                .formLogin()
//                .loginPage("/login")
//                .usernameParameter("username")
//                .passwordParameter("password")
//                .permitAll()
//                .successHandler(authenticationSuccessHandler()).permitAll()
//                .and()
//                .httpBasic()
//                .and()
//                .logout()
//                .logoutUrl("/logout") //设置logout请求的url
//                .logoutSuccessUrl("/logout?logout") //设置登出成功后跳转的url
//                .invalidateHttpSession(true) //使HttpSession失效
//                .deleteCookies("JSESSIONID") //删除指定的cookies
//                .and()
//                .headers().frameOptions().disable() //允许iframe 嵌套，跨域问题未允许
//                .and()
//                .csrf().disable();//csrf 关闭 CSRF保护机制,403错误可能是由于CSRF保护机制造成的
//
//        http.headers().contentTypeOptions().disable();
//
//        //1.HTTP响应标头
//        //https://docs.spring.io/spring-security/reference/servlet/exploits/headers.html#page-title
//        //http.headers()
//
//        //2.授权HTTP请求
//        //https://docs.spring.io/spring-security/reference/servlet/authorization/authorize-http-requests.html#page-title
//        //http.authorizeHttpRequests()
//
//        //3.基本认证
//        //http.httpBasic()
//
//
//        return http.build();
//    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/css/**", "/js/**", "/login", "/logout").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll()
                .successHandler(authenticationSuccessHandler())
                .and()
                .httpBasic()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/logout?logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .and()
                .headers().frameOptions().disable()
                .and()
                .csrf().disable();
    }


    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

    private static class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//            response.sendRedirect("/doc.html#/home");
            response.sendRedirect("/index");
        }
    }
}
