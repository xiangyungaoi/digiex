package com.caetp.digiex;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ServletComponentScan
@EnableScheduling // 开启定时任务
// @MapperScan("com.caetp.digiex.entity.mapper")
public class DigiExApplication {

	public static void main(String[] args) {
		SpringApplication.run(DigiExApplication.class, args);
	}
}
