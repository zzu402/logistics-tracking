package com.htkfood;
import java.util.Properties;

import javax.servlet.MultipartConfigElement;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import com.htkfood.interceptor.SecurityInterceptor;
import com.htkfood.ocr.OcrApiManager;


@Configuration
@ComponentScan("com.htkfood")
@MapperScan("com.htkfood.mapper")
@SpringBootApplication
public class App extends WebMvcConfigurerAdapter {
	private static Logger logger=LoggerFactory.getLogger(App.class);
    public static void main(String[] args){
    	logger.info("App is starting ...");
    	ConfigurableApplicationContext context = SpringApplication.run(App.class, args);
		Environment environment = context.getEnvironment();
		String appId = environment.getProperty("bd.ocr.appId", "");
		String secretKey = environment.getProperty("bd.ocr.secretKey", "");
		String apiKey = environment.getProperty("bd.ocr.apiKey", "");
		Properties properties = new Properties();
		properties.put("secretKey", secretKey);
		properties.put("appId", appId);
		properties.put("apiKey", apiKey);
		OcrApiManager.register("htkfood", properties);
        logger.info("App was started ...");
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(getSecurityInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
    @Bean
    SecurityInterceptor getSecurityInterceptor() {
        return new SecurityInterceptor();
    } 
    @Bean  
    public MultipartConfigElement multipartConfigElement() {  
        MultipartConfigFactory factory = new MultipartConfigFactory();  
        //单个文件最大  
        factory.setMaxFileSize("30MB"); //KB,MB  
        /// 设置总上传数据总大小  
        factory.setMaxRequestSize("100MB");  
        return factory.createMultipartConfig();  
    }  
}
