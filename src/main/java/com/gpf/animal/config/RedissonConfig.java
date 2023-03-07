//package com.gpf.animal.config;
//
//import org.redisson.Redisson;
//import org.redisson.api.RedissonClient;
//import org.redisson.config.Config;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * @author gengpengfei
// * @version V1.0
// * @ClassNane: RedissonConfig
// * @description: redis配置类
// * @date 2022/11/11 15:41
// */
//@Configuration
//public class RedissonConfig {
//
//    @Bean
//    public RedissonClient redissonClient() {
//        //创建Config对象
//        Config config = new Config();
//        //添加Redis地址 端口号
//        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
//        //创建Redis客户端并返回
//        return Redisson.create(config);
//    }
//
//}
