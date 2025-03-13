package com.example.suitcustomization.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import javax.annotation.PostConstruct;

/**
 * Web MVC 配置类
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

  /**
   * 构造后初始化方法，用于确认配置类被加载
   */
  @PostConstruct
  public void init() {
    System.out.println("==========WebMvcConfig已加载==========");
  }

  /**
   * 配置跨域资源共享
   * 
   * @param registry CORS注册表
   */
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    System.out.println("==========配置CORS==========");
    registry.addMapping("/**")
        .allowedOriginPatterns("*") // 允许所有来源，生产环境应该限制为特定域名
        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
        .allowedHeaders("*")
        .allowCredentials(true)
        .maxAge(3600); // 预检请求的有效期，单位为秒
  }
}