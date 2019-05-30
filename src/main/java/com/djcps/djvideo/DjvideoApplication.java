package com.djcps.djvideo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 有缘
 */
@SpringBootApplication
@MapperScan("com.djcps.djvideo.mapper")
public class DjvideoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DjvideoApplication.class, args);
    }

}
