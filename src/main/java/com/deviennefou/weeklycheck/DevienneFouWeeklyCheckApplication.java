package com.deviennefou.weeklycheck;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DevienneFouWeeklyCheckApplication {

	public static void main(String[] args) {
		SpringApplication.run(DevienneFouWeeklyCheckApplication.class, args);
	}

}
