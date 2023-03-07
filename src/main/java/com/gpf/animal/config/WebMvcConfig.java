package com.gpf.animal.config;

import com.gpf.animal.common.JacksonObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author gengpengfei
 * @description: WebMVC配置类
 * @date 2022/11/7 18:16
 */
@Slf4j
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport{
    /**
     * 设置静态资源映射
     *
     * @param registry
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("开始进行静态资源映射...");
        //                 请求地址                                   映射地址
        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
        registry.addResourceHandler("/frontend/**").addResourceLocations("classpath:/frontend/");
        log.info("静态资源映射成功");
    }

    /**
     * 扩展mvc框架的消息转化器
     *
     * @param converters
     */
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        //创建消息转化器对象
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        //设置对象转化器，底层用Jackson将Java对象转为JSON
        messageConverter.setObjectMapper(new JacksonObjectMapper());
        //将消息转化器对象追加到mvc框架的转换器集合中
        converters.add(0, messageConverter);
    }

}
