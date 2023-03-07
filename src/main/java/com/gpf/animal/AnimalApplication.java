package com.gpf.animal;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * @author gengpengfei
 */

@MapperScan("com.gpf.animal.dao")
@SpringBootApplication
@Slf4j

@ServletComponentScan
public class AnimalApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnimalApplication.class, args);
    }


}
