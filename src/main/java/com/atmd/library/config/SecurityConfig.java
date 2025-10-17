package com.atmd.library.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity // 开启Web安全功能
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //1. 定义用户
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.inMemoryAuthentication() // 使用内存存储用户信息
                .withUser("user") // 创建一个普通用户
                .password(passwordEncoder().encode("user123"))
                .roles("USER") // 赋予"USER"角色
                .and()
                .withUser("admin") // 创建一个管理员用户
                .password(passwordEncoder().encode("admin123"))
                .roles("ADMIN"); // 赋予"ADMIN"角色
    }

    // 2. 定义URL的访问权限规则
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .csrf().disable() // 暂时禁用csrf，方便Postman测试
                .authorizeRequests()
                // 允许任何人(permitAll)以GET方法访问/books及其子路径
                .antMatchers(HttpMethod.GET, "/books/**").permitAll()
                // 只允许拥有"ADMIN"角色的用户进行POST, PUT, DELETE操作
                .antMatchers(HttpMethod.POST, "/books").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/books/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/books/**").hasRole("ADMIN")
                // 其他所有请求都需要进行身份认证
                .anyRequest().authenticated()
                .and()
                .httpBasic(); // 启用HTTP Basic认证
    }

    // 3. 定义一个密码编码器Bean，Spring Security强制要求密码必须被加密存储
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}