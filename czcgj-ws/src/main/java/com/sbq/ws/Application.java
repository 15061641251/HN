package com.sbq.ws;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhangyuan on 2017/2/27.
 */
@ServletComponentScan
@EnableTransactionManagement
@RestController
@SpringBootApplication
@EnableAsync
@MapperScan(basePackages = "com.sbq.dao")
@ComponentScan(basePackages = "com.sbq")
public class Application extends SpringBootServletInitializer {


    @RequestMapping("/")
    public String hello() {
        return "Hello world!";
    }

    public static void main(String[] args) {
        new Application().configure(new SpringApplicationBuilder(Application.class)).run(args);
    }
}
