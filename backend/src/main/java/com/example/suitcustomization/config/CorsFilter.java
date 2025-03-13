package com.example.suitcustomization.config;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * CORS过滤器
 * 确保所有请求都经过CORS处理
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter {

  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) req;
    HttpServletResponse response = (HttpServletResponse) res;

    // 设置允许跨域的域名，*代表允许任何域名跨域
    response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
    // 设置允许的请求方法
    response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
    // 设置允许的请求头
    response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, X-Requested-With");
    // 设置预检请求的缓存时间
    response.setHeader("Access-Control-Max-Age", "3600");
    // 设置是否允许发送Cookie
    response.setHeader("Access-Control-Allow-Credentials", "true");

    // 对于OPTIONS请求直接返回200状态码
    if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
      response.setStatus(HttpServletResponse.SC_OK);
    } else {
      chain.doFilter(req, res);
    }
  }

  @Override
  public void init(FilterConfig filterConfig) {
    System.out.println("==========CORS过滤器已初始化==========");
  }

  @Override
  public void destroy() {
  }
}