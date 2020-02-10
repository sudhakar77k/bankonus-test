package com.pwstest.pws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author Sudhakar.k
 *
 */
@SpringBootApplication
@EnableAsync
@ComponentScan(basePackages = "com.pwstest.pws")
public class PwsApplication {

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(PwsApplication.class, args);
	}

}
