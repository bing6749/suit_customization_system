package com.suitcustom;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 西装定制管理系统启动类
 * 
 * @author suitcustom
 */
@SpringBootApplication
@EnableCaching
@MapperScan("com.suitcustom.mapper")
public class SuitCustomApplication {

  public static void main(String[] args) {
    SpringApplication.run(SuitCustomApplication.class, args);
  }

  /**
   * 全局CORS配置
   * 直接在主应用类中配置，确保被加载
   */
  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        System.out.println("==========主应用类中配置CORS==========");
        registry.addMapping("/**")
            .allowedOrigins("http://localhost:3003") // 明确指定前端域名
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .allowedHeaders("*")
            .allowCredentials(true)
            .maxAge(3600);
      }
    };
  }
}