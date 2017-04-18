package com.xia.reptile;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
//@EnableScheduling //定时器
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		
		int a = 0;
		Integer aa = a;
		Integer ab = a;
		System.out.println(aa == ab);
		System.out.println(aa.equals(ab));
		
		Integer ac = 127;
		Integer ad = 127;
		System.out.println(ac.equals(ad));
		System.out.println(ac == ad);
		
		Integer ae = 128;
		Integer af = 128;
		System.out.println(ae.equals(af));
		System.out.println(ae == af);
		
		Integer ag = -128;
		Integer ah = -128;
		System.out.println(ag.equals(ah));
		System.out.println(ag == ah);
		
		Integer ai = -129;
		Integer aj = -129;
		System.out.println(ai.equals(aj));
		System.out.println(ai == aj);
		
	}
	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			System.out.println("Let's inspect the beans provided by Spring Boot:");
			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName : beanNames) {
				System.out.println(beanName);
			}
		};
	}

}
