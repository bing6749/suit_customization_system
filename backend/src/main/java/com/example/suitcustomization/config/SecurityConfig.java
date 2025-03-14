package com.example.suitcustomization.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.PostConstruct;
import java.util.Arrays;

/**
 * Spring Security 配置类
 * 此配置类已被禁用，使用com.suitcustom.config.SecurityConfig代替
 */
// @Configuration
// @EnableWebSecurity
public class SecurityConfig {

  /**
   * 构造后初始化方法，用于确认配置类被加载
   */
  @PostConstruct
  public void init() {
    System.out.println("==========SecurityConfig已加载==========");
  }

  /**
   * 配置安全过滤链
   * 
   * @param http HttpSecurity对象
   * @return SecurityFilterChain
   * @throws Exception 配置异常
   */
  // @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    System.out.println("==========配置SecurityFilterChain==========");

    // 禁用CSRF，因为我们使用JWT进行认证
    http.csrf().disable();

    // 启用CORS
    http.cors();

    // 使用无状态会话
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    // 配置请求授权
    http.authorizeRequests()
        // 允许所有OPTIONS请求
        .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
        // 允许登录和注册接口匿名访问
        .antMatchers("/api/user/login", "/api/user/register").permitAll()
        // 其他请求需要认证
        .anyRequest().authenticated();

    return http.build();
  }

  /**
   * CORS配置源
   * 
   * @return CorsConfigurationSource
   */
  // @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    System.out.println("==========配置CorsConfigurationSource==========");

    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Arrays.asList("http://localhost:3001", "http://localhost:3003")); // 添加前端实际运行的端口
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With"));
    configuration.setAllowCredentials(true);
    configuration.setMaxAge(3600L);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}