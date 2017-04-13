package com.xia.reptile.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 过滤器
 * Xiams
 * @author xiams
 */
@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {

	private static Logger log = LoggerFactory.getLogger(WebMvcConfigurer.class);
	
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new HandlerInterceptorAdapter() {
			@Override
			public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
					Exception ex) throws Exception {
				log.info("----开始哦----");
				super.afterCompletion(request, response, handler, ex);
			}
		}).addPathPatterns("/**").excludePathPatterns("/swagger*/**", "/v2/api-docs");
	}
}
