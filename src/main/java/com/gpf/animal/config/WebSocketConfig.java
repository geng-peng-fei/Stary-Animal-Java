package com.gpf.animal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author gengpengfei
 * @version V1.0
 * @ClassNane: WebSocketConfig
 * @description: WebSocket配置
 * @date 2023/3/7 12:12
 */
@Configuration
public class WebSocketConfig {

    /**
     * 注入ServerEndpointExporter bean对象 自动注册使用了@ServerEndpoint注解的bean对象
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
