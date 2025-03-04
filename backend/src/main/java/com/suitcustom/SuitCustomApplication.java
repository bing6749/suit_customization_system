package com.suitcustom;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

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
}