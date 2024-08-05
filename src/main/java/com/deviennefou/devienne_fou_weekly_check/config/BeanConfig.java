package com.deviennefou.devienne_fou_weekly_check.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class BeanConfig {

    @Bean
    RestClient restClient(){
        return RestClient.create("raider.io");
    }
}
